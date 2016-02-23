package com.example.slidpage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button login;
	private TextView switchuser, zhuce, forget;
	private EditText username, password;
	private ImageView imageView;

	@SuppressLint("NewApi")
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
		imageView = (ImageView) findViewById(R.id.image);

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.loginimg);

		imageView.setBackground(new BitmapDrawable(getResources(),
				toRoundBitmap(bitmap)));
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String user = username.getText().toString().trim();
				String pwd = password.getText().toString().trim();

				SharedPreferences defaultSharedPreferences = PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this);
				String names = defaultSharedPreferences.getString("name", null);
				String pwds = defaultSharedPreferences.getString("pwd", null);
				if (user.equals(names) && pwd.equals(pwds)) {
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

		switchuser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				username.setText("");
				password.setText("");
			}
		});

		forget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences defaultSharedPreferences = PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this);

				if (defaultSharedPreferences.contains("name")) {
					defaultSharedPreferences.edit().remove("name").commit();
				}
				if (defaultSharedPreferences.contains("pwd")) {
					defaultSharedPreferences.edit().remove("pwd").commit();
				}

				Toast.makeText(LoginActivity.this, "请重新注册", Toast.LENGTH_SHORT)
						.show();

				Intent intent = new Intent(LoginActivity.this,
						ZhuCeActivity.class);
				startActivity(intent);
			}
		});

		zhuce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						ZhuCeActivity.class);
				startActivity(intent);
			}
		});
	}

	public Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2 - 5;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2 - 5;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst_left + 15, dst_top + 15,
				dst_right - 20, dst_bottom - 20);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}
}
