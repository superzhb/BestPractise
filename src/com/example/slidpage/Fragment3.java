package com.example.slidpage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.slidpage.model.Message;
import com.google.gson.Gson;

public class Fragment3 extends Fragment {

	private RequestQueue queue;
	private StringRequest request;
	private ListView listView;
	private MyAdapter adaptor;
	private List<Message> messages;
	private Button send;
	private EditText msg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.activity_tuling, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) getActivity().findViewById(R.id.list);
		messages = new ArrayList<Message>();
		adaptor = new MyAdapter(getActivity(), R.layout.activity_tl, messages);
		send = (Button) getActivity().findViewById(R.id.send);
		msg = (EditText) getActivity().findViewById(R.id.msg);
		listView.setAdapter(adaptor);
		queue = Volley.newRequestQueue(getActivity());

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String text = msg.getText().toString();
				if (text.trim().length() <= 0) {
					return;
				}
				msg.setText("");
				Message message = new Message();
				message.setCode("1");
				message.setText(text);
				messages.add(message);
				adaptor.notifyDataSetChanged();
				scrollMyListViewToBottom();
				try {
					request = new StringRequest(
							"http://www.tuling123.com/openapi/api?key=77bd495ec6d3290a4173702f97e898a5&info="
									+ URLEncoder.encode(text, "utf-8"),
							new Listener<String>() {

								@Override
								public void onResponse(String response) {
									Gson gson = new Gson();

									Message message = new Message();
									message = gson.fromJson(response,
											Message.class);
									messages.add(message);
									Log.e("", message.getCode());
									adaptor.notifyDataSetChanged();
									scrollMyListViewToBottom();
								}
							}, new ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError error) {

								}
							});
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				queue.add(request);
			}
		});

	}

	class MyAdapter extends ArrayAdapter<Message> {

		public MyAdapter(Context context, int resource, List<Message> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Message message = messages.get(position);

			convertView = LayoutInflater.from(getActivity()).inflate(
					R.layout.activity_tl, null);
			TextView textView = (TextView) convertView
					.findViewById(R.id.textid);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.headimage);
			TextView blank = (TextView) convertView.findViewById(R.id.blank);
			if (message.getCode().equals("1")) {
				imageView.setImageResource(R.drawable.head);
				ViewGroup group = (ViewGroup) imageView.getParent();
				group.removeView(imageView);
				group.addView(imageView);
				blank.setVisibility(View.INVISIBLE);
				textView.setText(message.getText());
			} else if (message.getCode().equals("200000")) {
				textView.setText(message.getUrl());
			} else if (message.getCode().equals("100000")) {
				textView.setText(message.getText());
			}
			return convertView;
		}
	}

	private void scrollMyListViewToBottom() {
		listView.post(new Runnable() {
			@Override
			public void run() {
				listView.setSelection(adaptor.getCount() - 1);
			}
		});
	}
}
