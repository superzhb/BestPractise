package com.example.slidpage.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.slidpage.R;

public class PullToRefeshView extends LinearLayout implements OnTouchListener {
	public static final int DOWN = 0;
	public static final int FINISH = 0;
	public static final int DOING = 0;
	public static final int RELEASE = 0;
	private static final String UPDATE_TIME = "updatetime";

	private ImageView imageView;
	private ProgressBar progressBar;
	private TextView textDate, textStatus;
	private SharedPreferences preferences;
	private int id;
	private boolean isload = false;
	private ListView listView;
	private View viewhead;
	private float down_y, up_y, move_y, headMarginTop;
	private LayoutParams layoutParams;
	private OnpullTofreshLisenter lisenter;
	private int currentStatus;

	public PullToRefeshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		preferences = PreferenceManager.getDefaultSharedPreferences(context);

		viewhead = LayoutInflater.from(getContext()).inflate(
				R.layout.layout_pulltofresh_head, null);
		imageView = (ImageView) viewhead.findViewById(R.id.image);
		progressBar = (ProgressBar) viewhead.findViewById(R.id.pro);
		textDate = (TextView) viewhead.findViewById(R.id.date);
		textStatus = (TextView) viewhead.findViewById(R.id.status);

		setOrientation(VERTICAL);

		addView(viewhead, 0);

		updateTime();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && !isload) {
			headMarginTop = -viewhead.getHeight();
			layoutParams = (LayoutParams) viewhead.getLayoutParams();
			layoutParams.topMargin = (int) headMarginTop;
			listView = (ListView) getChildAt(1);
			listView.setOnTouchListener(this);
			isload = true;
		}
	}

	private void updateTime() {
		long lastupdate = preferences.getLong(UPDATE_TIME + id,
				System.currentTimeMillis());

		long time = lastupdate - System.currentTimeMillis();
		if (time <= 1000 * 60) {
			textDate.setText("刚刚刷新过");
		} else if (time > 1000 * 60 && time <= 1000 * 60 * 60) {
			textDate.setText("刚刚刷新过" + time / 1000 / 60 + "分钟");
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			down_y = event.getY();
			return false;
		case MotionEvent.ACTION_MOVE:
			move_y = event.getY();
			int distance = (int) (move_y - down_y);
			if (distance > 2 * viewhead.getHeight()) {
				distance = 2 * viewhead.getHeight();
			}
			layoutParams.topMargin = (int) (headMarginTop + distance / 2);
			viewhead.setLayoutParams(layoutParams);
			return false;
		case MotionEvent.ACTION_UP:
			up_y = event.getY();
			if(lisenter != null){
				lisenter.refresh();
			}

			return false;

		default:
			break;
		}
		return false;
	}

	public void setOnpullTofreshLisenter(OnpullTofreshLisenter lisenter) {
		this.lisenter = lisenter;
	}

	public interface OnpullTofreshLisenter {
		void refresh();
	}

	public void finishRresh() {

	}
}
