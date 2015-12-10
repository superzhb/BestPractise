package com.example.slidpage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.slidpage.model.Weather;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity implements OnClickListener {
	private Button search;
	private EditText city;
	private TextView city1, temp, wind, shidu, fengxiang, richu, riluo, alarm;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		// ActionBar锟斤拷锟矫凤拷锟截革拷页锟斤拷陌锟脚�

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		city = (EditText) findViewById(R.id.wecity);
		search = (Button) findViewById(R.id.weserrch);
		search.setOnClickListener(this);

		city1 = (TextView) findViewById(R.id.city);
		temp = (TextView) findViewById(R.id.temp);
		wind = (TextView) findViewById(R.id.wind);
		shidu = (TextView) findViewById(R.id.shidu);
		fengxiang = (TextView) findViewById(R.id.fengxiang);
		richu = (TextView) findViewById(R.id.richu);
		riluo = (TextView) findViewById(R.id.riluo);
		alarm = (TextView) findViewById(R.id.alarm);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weserrch:
			String cityName = city.getText().toString();
			new SerachWeatherTask().execute(cityName);
			break;

		default:
			break;
		}
	}

	class SerachWeatherTask extends AsyncTask<String, String, Weather> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();
			try {
				URL url = new URL("http://wthrcdn.etouch.cn/WeatherApi?city="
						+ URLEncoder.encode(params[0], "UTF-8"));
				publishProgress(url.getHost() + url.getPath() + url.getQuery());
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				XmlPullParser pullParser = Xml.newPullParser();
				pullParser.setInput(connection.getInputStream(), "UTF-8");
				int eventKey = pullParser.getEventType();
				while (eventKey != XmlPullParser.END_DOCUMENT) {
					switch (eventKey) {
					case XmlPullParser.START_TAG:
						if ("city".equals(pullParser.getName())) {
							weather.setCity(pullParser.nextText());
						}
						if ("wendu".equals(pullParser.getName())) {
							weather.setTemp(pullParser.nextText());
						}
						if ("fengli".equals(pullParser.getName())) {
							weather.setWind(pullParser.nextText());
						}
						if ("shidu".equals(pullParser.getName())) {
							weather.setShidu(pullParser.nextText());
						}
						if ("fengxiang".equals(pullParser.getName())) {
							weather.setFengxiang(pullParser.nextText());
						}
						if ("sunrise_1".equals(pullParser.getName())) {
							weather.setRichu(pullParser.nextText());
						}
						if ("sunset_1".equals(pullParser.getName())) {
							weather.setRiluo(pullParser.nextText());
						}
						if ("suggest".equals(pullParser.getName())) {
							weather.setAlarm(pullParser.nextText());
						}
						break;

					default:
						break;
					}
					eventKey = pullParser.next();
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			return weather;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			Toast.makeText(WeatherActivity.this, values[0], Toast.LENGTH_SHORT)
					.show();
			Log.e("", values[0]);
		}

		@Override
		protected void onPostExecute(Weather result) {
			super.onPostExecute(result);
			city1.setText(result.getCity());
			temp.setText(result.getTemp());
			wind.setText(result.getWind());
			shidu.setText(result.getShidu());
			fengxiang.setText(result.getFengxiang());
			richu.setText(result.getRichu());
			riluo.setText(result.getRiluo());
			alarm.setText(result.getAlarm());
		}

	}
}
