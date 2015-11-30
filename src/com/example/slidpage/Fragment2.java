package com.example.slidpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.slidpage.util.PullToRefeshView;
import com.example.slidpage.util.PullToRefeshView.PullToRefreshListener;

public class Fragment2 extends Fragment {
	private ListView listView;
	private PullToRefeshView pullToRefeshView;
	private ArrayAdapter<String> adapter;
	String[] strs = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragmen2, null);
		pullToRefeshView = (PullToRefeshView) view
				.findViewById(R.id.pulltorefresh);
		listView = (ListView) view.findViewById(R.id.mylistview);
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, strs);
		listView.setAdapter(adapter);
		pullToRefeshView.setOnRefreshListener(new PullToRefreshListener() {

			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pullToRefeshView.finishRefreshing();
			}
		}, 1);
		return view;
	}
}
