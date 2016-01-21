package com.example.slidpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button login;
	private TextView switchuser, zhuce, forget;
	private EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		login = (Button) findViewById(R.id.login);
		switchuser = (TextView) findViewById(R.id.switchuser);
		zhuce = (TextView) findViewById(R.id.zhuce);
		forget = (TextView) findViewById(R.id.forget);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String user = username.getText().toString().trim();
				String pwd = password.getText().toString().trim();
				if (user.equals("xiaoxiao") && pwd.equals("xiaoxiao")) {
					Intent intent = new Intent(LoginActivity.this,
							IndexActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(LoginActivity.this, "用户名或者密码错误",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}
}
