package com.example.slidpage;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.slidpage.util.PagerSlidingTabStrip;

@SuppressLint("NewApi")
public class IndexActivity extends FragmentActivity {
	private ViewPager pager;
	private Fragment fragment1, fragment2, fragment3;
	private FragmentManager fragmentManager;
	private String[] title = new String[] { "首页", "日记", "机器人小一" };
	private PagerSlidingTabStrip tabStrip;
	private DisplayMetrics dm;
	private SearchView searchView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		dm = getResources().getDisplayMetrics();
		tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		tabStrip.setShouldExpand(true);
		tabStrip.setTextColor(Color.parseColor("#F7C019"));
		tabStrip.setIndicatorColor(Color.parseColor("#F7C019"));
		tabStrip.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		tabStrip.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		tabStrip.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 15, dm));
		initViewPager();

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

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.maserrch);
		MenuItem tologin = menu.findItem(R.id.tologin);
		MenuItem weather = menu.findItem(R.id.maweather);
		MenuItem mapphoto = menu.findItem(R.id.mapphoto);
		// 图片按钮
		mapphoto.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(IndexActivity.this,
						PicActivity.class);
				startActivity(intent);
				return false;
			}
		});
		// 搜索按钮
		searchView = (SearchView) item.getActionView();
		searchView.setOnSearchClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(IndexActivity.this, "21212", Toast.LENGTH_SHORT)
						.show();
			}
		});
		item.setOnActionExpandListener(new OnActionExpandListener() {

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				return false;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				return false;
			}
		});

		// 到登陆界面
		tologin.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(IndexActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
				return false;
			}
		});
		// 天气按钮
		weather.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(IndexActivity.this,
						WeatherActivity.class);
				startActivity(intent);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	private void initViewPager() {
		pager = (ViewPager) findViewById(R.id.indexviewpager);
		fragment1 = new Fragment1();
		fragment2 = new Fragment2();
		fragment3 = new Fragment3();
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		fragmentManager = getSupportFragmentManager();
		pager.setAdapter(new MyFragmentPagerAdaptor(fragmentManager));
		// 设置 缓存 的个数
		pager.setOffscreenPageLimit(2);
		tabStrip.setViewPager(pager);
	}

	class MyFragmentPagerAdaptor extends FragmentPagerAdapter {

		public MyFragmentPagerAdaptor(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return title[position];
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return fragment1;
			case 1:
				return fragment2;
			case 2:
				return fragment3;
			default:
				break;
			}
			return fragment1;
		}

		@Override
		public int getCount() {
			return title.length;
		}

	}

}
