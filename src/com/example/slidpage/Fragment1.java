package com.example.slidpage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment1 extends Fragment {
	private TextView tv;
	private SwipeRefreshLayout swipeRefreshLayout;
	private LinearLayout layout;

	@SuppressLint("InlinedApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragmen1, null);
		tv = (TextView) view.findViewById(R.id.textView1);
		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);
		layout = (LinearLayout) view.findViewById(R.id.swipe_layout);
		// 设置刷新时动画的颜色，可以设置4个
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				layout.setVisibility(View.VISIBLE);
				layout.setBackgroundColor(Color.BLACK);
				tv.setText("正在刷新...");
				tv.setTextColor(Color.WHITE);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						layout.setVisibility(View.GONE);
						swipeRefreshLayout.setRefreshing(false);
					}
				}, 6000);
			}
		});
		return view;
	}
}
