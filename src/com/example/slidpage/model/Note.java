package com.example.slidpage.model;

public class Note implements Comparable<Note> {
	private int id;
	private String content;
	private String date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int compareTo(Note another) {
		return content.compareTo(another.getContent());
	}

}
