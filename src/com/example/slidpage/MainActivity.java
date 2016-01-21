package com.example.slidpage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	private View view1, view2, view3, view4;
	private ViewPager viewPager;
	private List<View> views = new ArrayList<View>();
	private int currentIndex = 0;
	private RadioButton r1, r2, r3, r4;
	private RadioButton[] buttons;
	private TextView in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initViewPager();

		r1 = (RadioButton) findViewById(R.id.r1);
		r2 = (RadioButton) findViewById(R.id.r2);
		r3 = (RadioButton) findViewById(R.id.r3);
		r4 = (RadioButton) findViewById(R.id.r4);
		buttons = new RadioButton[] { r1, r2, r3, r4 };
		in = (TextView) findViewById(R.id.in);
		in.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		view1 = getLayoutInflater().inflate(R.layout.layout1, null);
		view2 = getLayoutInflater().inflate(R.layout.layout2, null);
		view3 = getLayoutInflater().inflate(R.layout.layout3, null);
		view4 = getLayoutInflater().inflate(R.layout.layout4, null);

		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);

		viewPager.setAdapter(new MyViewPagerAdaptor());
		viewPager.setCurrentItem(currentIndex);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				in.setText("");
				if (position == views.size() - 1) {
					in.setText("开始体验");
				}
				currentIndex = position;
				for (RadioButton button : buttons) {
					button.setBackgroundResource(R.drawable.round);
				}
				buttons[position]
						.setBackgroundResource(R.drawable.selected_dot);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	class MyViewPagerAdaptor extends PagerAdapter {

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

	}

}
