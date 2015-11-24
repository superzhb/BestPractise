package com.example.slidpage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;

public class IndexActivity extends FragmentActivity implements OnClickListener {
	private TextView t1, t2, t3, t4;
	private ViewPager pager;
	private View view1, view2, view3, view4;
	private List<View> views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		initViewPager();
		initText();
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void initText() {
		t1 = (TextView) findViewById(R.id.inmsg);
		t2 = (TextView) findViewById(R.id.inpeo);
		t3 = (TextView) findViewById(R.id.inser);
		t4 = (TextView) findViewById(R.id.inset);
		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
		t3.setOnClickListener(this);
		t4.setOnClickListener(this);
		t1.setTextColor(Color.BLUE);
	}

	private void initViewPager() {
		pager = (ViewPager) findViewById(R.id.indexviewpager);
		views = new ArrayList<View>();
		view1 = getLayoutInflater().inflate(R.layout.activity_fragmen1, null);
		view2 = getLayoutInflater().inflate(R.layout.activity_fragmen2, null);
		view3 = getLayoutInflater().inflate(R.layout.activity_fragmen3, null);
		view4 = getLayoutInflater().inflate(R.layout.activity_fragmen4, null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				clearColor();
				switch (position) {
				case 0:
					t1.setTextColor(Color.BLUE);
					break;
				case 1:
					t2.setTextColor(Color.BLUE);
					break;
				case 2:
					t3.setTextColor(Color.BLUE);
					break;
				case 3:
					t4.setTextColor(Color.BLUE);
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		pager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {

				container.addView(views.get(position));
				return views.get(position);
			}
		});
	}

	@Override
	public void onClick(View v) {
		clearColor();
		setColor(v.getId());
	}

	private void setColor(int i) {
		switch (i) {
		case R.id.inmsg:
			pager.setCurrentItem(0);
			t1.setTextColor(Color.BLUE);
			break;
		case R.id.inpeo:
			pager.setCurrentItem(1);
			t2.setTextColor(Color.BLUE);
			break;
		case R.id.inser:
			pager.setCurrentItem(2);
			t3.setTextColor(Color.BLUE);
			break;
		case R.id.inset:
			pager.setCurrentItem(3);
			t4.setTextColor(Color.BLUE);
			break;
		default:
			break;
		}
	}

	private void clearColor() {
		t1.setTextColor(Color.BLACK);
		t2.setTextColor(Color.BLACK);
		t3.setTextColor(Color.BLACK);
		t4.setTextColor(Color.BLACK);
	}
}
