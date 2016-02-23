package com.example.slidpage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZhuCeActivity extends Activity implements OnClickListener {
	private Button okzhuce, canzhuce;
	private EditText name, pwd, enpwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhuce);
		okzhuce = (Button) findViewById(R.id.okzhuce);
		canzhuce = (Button) findViewById(R.id.canzhuce);
		name = (EditText) findViewById(R.id.username);
		pwd = (EditText) findViewById(R.id.password);
		enpwd = (EditText) findViewById(R.id.enpassword);

		okzhuce.setOnClickListener(this);
		canzhuce.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.okzhuce:
			String names = name.getText().toString().trim();
			String pwds = pwd.getText().toString().trim();
			String enpwds = enpwd.getText().toString().trim();

			if (names == null || pwd == null || pwds == null
					|| names.equals("") || pwd.equals("") || pwds.equals("")) {
				return;
			}
			if (!pwds.equals(enpwds)) {
				Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
				return;
			}

			SharedPreferences defaultSharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(ZhuCeActivity.this);
			if (!names.equals(defaultSharedPreferences.getString("name", null))) {
				Editor edit = defaultSharedPreferences.edit();
				edit.putString("name", names);
				edit.commit();
			} else {
				Toast.makeText(this, "用户已经存在", Toast.LENGTH_SHORT).show();
				return;
			}

			if (!pwds.equals(defaultSharedPreferences.getString("pwd", null))) {
				Editor edit = defaultSharedPreferences.edit();
				edit.putString("pwd", pwds);
				edit.commit();
			}
			Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.canzhuce:
			finish();
			break;
		default:
			break;
		}
	}

}
