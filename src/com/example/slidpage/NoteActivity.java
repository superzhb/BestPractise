package com.example.slidpage;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NoteActivity extends Activity implements OnClickListener {
	private Button ok, cancel;
	private ContentResolver resolver;
	private EditText text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);

		ok = (Button) findViewById(R.id.ok);
		cancel = (Button) findViewById(R.id.cancel);
		text = (EditText) findViewById(R.id.edit);
		resolver = getContentResolver();

		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok:
			new SaveTask().execute();
			finish();
			break;
		case R.id.cancel:
			finish();
			break;
		default:
			break;
		}
	}

	class SaveTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			ContentValues values = new ContentValues();
			values.put("content", text.getText().toString());
			values.put("date", formateDate());
			resolver.insert(
					Uri.parse("content://com.example.slidpage.contentprovider/note"),
					values);
			return null;
		}

	}

	private String formateDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date(System.currentTimeMillis()));
	}

}
