package com.example.slidpage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import com.example.slidpage.index.IndexableListView;
import com.example.slidpage.index.StringMatcher;

public class Fragment1 extends Fragment {
	private SwipeRefreshLayout swipeRefreshLayout;
	private IndexableListView indexableListView;
	private List<String> list;

	@SuppressLint("InlinedApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragmen1, null);
		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);

		indexableListView = (IndexableListView) view
				.findViewById(R.id.listview);

		initlist();
		Collections.sort(list);
		MyListAdaptor adaptor = new MyListAdaptor(getActivity(),
				android.R.layout.simple_list_item_1, list);
		indexableListView.setAdapter(adaptor);

		indexableListView.setFastScrollEnabled(true);

		// 设置刷新时动画的颜色，可以设置4个
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(false);
					}
				}, 6000);
			}
		});

		return view;
	}

	class MyListAdaptor extends ArrayAdapter<String> implements SectionIndexer {

		private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		public MyListAdaptor(Context context, int resource, List<String> objects) {
			super(context, resource, objects);
		}

		@Override
		public Object[] getSections() {
			String[] sections = new String[mSections.length()];
			for (int i = 0; i < mSections.length(); i++)
				sections[i] = String.valueOf(mSections.charAt(i));
			return sections;
		}

		@Override
		public int getPositionForSection(int section) {
			for (int i = section; i >= 0; i--) {
				for (int j = 0; j < getCount(); j++) {
					if (i == 0) {
						for (int k = 0; k <= 9; k++) {
							if (StringMatcher.match(
									String.valueOf(getItem(j).charAt(0)),
									String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(
								String.valueOf(getItem(j).charAt(0)),
								String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

	}

	private void initlist() {
		list = new ArrayList<String>();
		list.add("A1");
		list.add("B2");
		list.add("C2");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("H");
		list.add("J");
		list.add("K");
		list.add("P");
		list.add("Y");
		list.add("U");
		list.add("O");
		list.add("T");
		list.add("G");
		list.add("R");
		list.add("E");
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("H");
		list.add("J");
		list.add("K");
		list.add("P");
		list.add("Y");
		list.add("U");
		list.add("O");
		list.add("T");
		list.add("G");
		list.add("R");
		list.add("E");
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("H");
		list.add("J");
		list.add("K");
		list.add("P");
		list.add("Y");
		list.add("U");
		list.add("O");
		list.add("T");
		list.add("G");
		list.add("R");
		list.add("E");
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("H");
		list.add("J");
		list.add("K");
		list.add("P");
		list.add("Y");
		list.add("U");
		list.add("O");
		list.add("T");
		list.add("G");
		list.add("R");
		list.add("E");
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("H");
		list.add("J");
		list.add("K");
		list.add("P");
		list.add("Y");
		list.add("U");
		list.add("O");
		list.add("T");
		list.add("G");
		list.add("R");
		list.add("E");
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("H");
		list.add("J");
		list.add("K");
		list.add("P");
		list.add("Y");
		list.add("U");
		list.add("O");
		list.add("T");
		list.add("G");
		list.add("R");
		list.add("E");
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("H");
		list.add("J");
		list.add("K");
		list.add("P");
		list.add("Y");
		list.add("U");
		list.add("O");
		list.add("T");
		list.add("G");
		list.add("R");
		list.add("E");
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("H");
		list.add("J");
		list.add("K");
		list.add("P");
		list.add("Y");
		list.add("U");
		list.add("O");
		list.add("T");
		list.add("G");
		list.add("R");
		list.add("E");
	}
}
