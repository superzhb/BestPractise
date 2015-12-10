package com.example.slidpage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.slidpage.index.IndexableListView;
import com.example.slidpage.index.StringMatcher;
import com.example.slidpage.model.Note;

@SuppressLint("NewApi")
public class Fragment1 extends Fragment implements OnClickListener,
		LoaderCallbacks<Cursor> {
	private SwipeRefreshLayout swipeRefreshLayout;
	private IndexableListView indexableListView;
	private List<Note> list;
	private Button button;
	private LoaderManager loaderManager;
	private ArrayAdapter<Note> adapter;

	@SuppressLint("InlinedApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragmen1, null);
		button = (Button) view.findViewById(R.id.add);
		button.setOnClickListener(this);
		loaderManager = this.getLoaderManager();
		loaderManager.initLoader(1, null, this);
		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);

		indexableListView = (IndexableListView) view
				.findViewById(R.id.listview);

		indexableListView.setAdapter(adapter);

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

	class MyListAdaptor extends ArrayAdapter<Note> implements SectionIndexer {

		private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		public MyListAdaptor(Context context, int resource, List<Note> objects) {
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
							if (StringMatcher
									.match(String.valueOf((getItem(j))
											.getContent().charAt(0)), String
											.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(String.valueOf(getItem(j)
								.getContent().charAt(0)), String
								.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler hodler = null;
			if (convertView == null) {
				hodler = new ViewHodler();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.content_layout, null);
				hodler.content = (TextView) convertView
						.findViewById(R.id.content_content);
				hodler.date = (TextView) convertView
						.findViewById(R.id.content_date);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
			}
			Note note = getItem(position);
			hodler.content.setText(note.getContent());
			hodler.date.setText(note.getDate());

			return convertView;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

	}

	public static class ViewHodler {
		private TextView content, date;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:
			Intent intent = new Intent(getActivity(), NoteActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(
				getActivity(),
				Uri.parse("content://com.example.slidpage.contentprovider/note"),
				null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		list = new ArrayList<Note>();
		while (cursor.moveToNext()) {
			Note note = new Note();
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			note.setContent(content);
			note.setDate(date);
			list.add(note);
		}
		Collections.sort(list);
		adapter = new MyListAdaptor(getActivity(), R.layout.content_layout,
				list);
		indexableListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

}
