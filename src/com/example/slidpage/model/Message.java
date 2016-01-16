package com.example.slidpage.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {
	private int id;
	private String code;
	private String text;
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(code);
		dest.writeString(text);
		dest.writeString(url);

	}

	public static final Parcelable.Creator<Message> CREATOR = new Creator<Message>() {

		@Override
		public Message[] newArray(int size) {
			return new Message[size];
		}

		@Override
		public Message createFromParcel(Parcel source) {
			return new Message(source);
		}
	};

	private Message(Parcel parcel) {
		id = parcel.readInt();
		code = parcel.readString();
		text = parcel.readString();
		url = parcel.readString();
	}

	public Message() {
	}
}
