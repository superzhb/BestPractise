package com.example.slidpage;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.util.LruCache;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class MusicActivity extends Activity {
	private ActionBar actionBar;
	private ImageView imageView;
	private Options options;
	private LruCache<String, Bitmap> cache;
	private LinearLayout layout;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				imageView.setImageBitmap((Bitmap) msg.obj);
				layout = (LinearLayout) findViewById(R.id.line);
				layout.addView(imageView);
				layout.invalidate();
				break;

			default:
				break;
			}
		};
	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		initLruCache();

		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				imageView = new ImageView(MusicActivity.this);
				int imageId = R.drawable.a1;
				if (cache.get(String.valueOf(imageId)) != null) {
					Message message = new Message();
					message.obj = cache.get(String.valueOf(imageId));
					message.what = 1;
					handler.sendMessage(message);
				} else {
					options = new Options();
					options.inJustDecodeBounds = true;
					int simpleSize = getSimpleSize(imageId);

					options.inSampleSize = simpleSize;
					options.inJustDecodeBounds = false;

					Bitmap bitmap1 = BitmapFactory.decodeResource(
							getResources(), imageId, options);
					cache.put(imageId + "", bitmap1);
					Message message = new Message();
					message.obj = bitmap1;
					message.what = 1;
					handler.sendMessage(message);
				}
			}
		}, 0, 2000);

	}

	// 初始化LruCache
	private void initLruCache() {
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		cache = new LruCache<String, Bitmap>(maxMemory / 4) {

			// 每张图片的大小(Kb)，如果不重写此方法默认返回1
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount() / 1024;
			}
		};
	}

	public void addBitMapToLruCache(String key, Bitmap bitmap) {
		if (cache.get(key) == null) {
			cache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromLruCahce(String key) {
		return cache.remove(key);
	}

	private int getSimpleSize(int imageId) {
		BitmapFactory.decodeResource(getResources(), imageId, options);

		int width = options.outHeight;
		int height = options.outHeight;
		int desWidth = 100;
		int desHeight = 100;
		double w = width / desWidth;
		double h = height / desHeight;
		int simpleSize = (int) (h > w ? w : h);
		return simpleSize;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		}
		return false;
	}
}
