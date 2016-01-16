package com.example.slidpage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.slidpage.util.ImageLoader;

@SuppressLint("NewApi")
public class PicActivity extends Activity {
	private ActionBar actionBar;
	private LinearLayout linearLayout1, linearLayout2, linearLayout3;
	private ImageLoader imageLoader;
	private int width;
	private int height1 = 0;
	private int height2 = 0;
	private int height3 = 0;

	private ScrollView scrollView;
	private List<ImageView> imageViews = new ArrayList<ImageView>();

	private int[] images = new int[] { R.drawable.p2, R.drawable.p2,
			R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
			R.drawable.p7, R.drawable.p8, R.drawable.p9 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic);

		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Point outSize = new Point();
		getWindowManager().getDefaultDisplay().getSize(outSize);
		width = outSize.x / 3;
		linearLayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
		linearLayout2 = (LinearLayout) findViewById(R.id.linearlayout2);
		linearLayout3 = (LinearLayout) findViewById(R.id.linearlayout3);
		scrollView = (ScrollView) findViewById(R.id.scroll);

		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (scrollView.getChildAt(0).getHeight() == scrollView
							.getScrollY() + scrollView.getHeight()) {
						loadImage(20);
					}
				}
				return false;
			}

		});
		loadImage(20);
	}

	private void loadImage(int qua) {
		imageLoader = ImageLoader.getInstance();
		Random random = new Random();
		for (int i = 0; i < qua; i++) {
			Bitmap bitmap = imageLoader.getBitmap(getResources(),
					images[random.nextInt(images.length)], width);
			ImageView imageView = new ImageView(this);
			imageView.setPadding(2, 2, 2, 2);
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ScaleType.FIT_XY);
			if (height1 <= height2) {
				if (height1 <= height3) {
					linearLayout1.addView(imageView);
					height1 += bitmap.getHeight();
					imageView.setTag(R.string.bottom,
							height1 + bitmap.getHeight());
					imageView.setTag(R.string.top, height1);
				} else {
					linearLayout3.addView(imageView);
					height3 += bitmap.getHeight();
					imageView.setTag(R.string.bottom,
							height3 + bitmap.getHeight());
					imageView.setTag(R.string.top, height3);
				}
			} else {
				if (height2 <= height3) {
					linearLayout2.addView(imageView);
					height2 += bitmap.getHeight();
					imageView.setTag(R.string.bottom,
							height2 + bitmap.getHeight());
					imageView.setTag(R.string.top, height2);
				} else {
					linearLayout3.addView(imageView);
					height3 += bitmap.getHeight();
					imageView.setTag(R.string.bottom,
							height3 + bitmap.getHeight());
					imageView.setTag(R.string.top, height3);
				}
			}
			imageViews.add(imageView);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 增加ActionBar返回主界面功能
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
