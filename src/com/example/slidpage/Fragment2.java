package com.example.slidpage;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.slidpage.index.IndexableListView;
import com.example.slidpage.index.StringMatcher;

@SuppressLint("NewApi")
public class Fragment2 extends Fragment implements OnClickListener,
		LoaderCallbacks<Cursor> {
	// private SwipeRefreshLayout swipeRefreshLayout;
	private IndexableListView indexableListView;
	private Button button, delbtn;
	private LoaderManager loaderManager;
	private MyListAdaptor adapter;
	private CursorLoader loader;
	private TextView textid;
	private ContentResolver resolver;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		button = (Button) getActivity().findViewById(R.id.add);
		button.setOnClickListener(this);
		resolver = getActivity().getContentResolver();
		loaderManager = this.getLoaderManager();
		loaderManager.initLoader(1, null, this);

		indexableListView = (IndexableListView) getActivity().findViewById(
				R.id.listview);

		adapter = new MyListAdaptor(getActivity(), R.layout.content_layout,
				null, new String[] { "content", "date", "encode", "_id" },
				new int[] { R.id.content_content, R.id.content_date,
						R.id.content_encode, R.id.content_id }, 0);
		indexableListView.setAdapter(adapter);

		indexableListView.setFastScrollEnabled(true);
		indexableListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (null != delbtn && delbtn.getVisibility() == View.VISIBLE) {
					delbtn.setVisibility(View.GONE);
				}
				return false;
			}
		});

		indexableListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (null != delbtn && delbtn.getVisibility() == View.VISIBLE) {
					delbtn.setVisibility(View.GONE);
					return;
				}
				delbtn = (Button) view.findViewById(R.id.del);
				delbtn.setVisibility(View.VISIBLE);
				textid = (TextView) view.findViewById(R.id.content_id);
				delbtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Uri url = Uri
								.parse("content://com.example.slidpage.contentprovider/note/"
										+ textid.getText());
						int deleteId = resolver.delete(url, null, null);
						if (deleteId > -1) {
							loaderManager
									.restartLoader(1, null, Fragment2.this);
						}
					}
				});
			}
		});
	}

	@SuppressLint("InlinedApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_fragmen1, null);

	}

	class MyListAdaptor extends SimpleCursorAdapter implements SectionIndexer {

		public MyListAdaptor(Context context, int layout, Cursor c,
				String[] from, int[] to, int flags) {
			super(context, layout, c, from, to, flags);
		}

		private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
							if (StringMatcher.match(String
									.valueOf(((Cursor) getItem(j)).getString(
											((Cursor) getItem(j))
													.getColumnIndex("encode"))
											.charAt(0)), String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher
								.match(String
										.valueOf(
												((Cursor) getItem(j))
														.getString(
																((Cursor) getItem(j))
																		.getColumnIndex("encode"))
														.charAt(0))
										.toUpperCase(), String
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
				hodler.encode = (TextView) convertView
						.findViewById(R.id.content_encode);
				hodler.id = (TextView) convertView
						.findViewById(R.id.content_id);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
			}
			Cursor cursor = (Cursor) getItem(position);
			hodler.content.setText(cursor.getString(cursor
					.getColumnIndex("content")));
			hodler.date
					.setText(cursor.getString(cursor.getColumnIndex("date")));

			hodler.encode.setText(cursor.getString(cursor
					.getColumnIndex("encode")));

			hodler.id.setText(cursor.getString(cursor.getColumnIndex("_id")));

			return convertView;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

	}

	public static class ViewHodler {
		private TextView content, date, encode, id;
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
		loader = new CursorLoader(
				getActivity(),
				Uri.parse("content://com.example.slidpage.contentprovider/note"),
				null, null, null, "encode");
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
		indexableListView.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	@Override
	public void onResume() {
		super.onResume();
		loaderManager.restartLoader(1, null, this);
	}

}
