package org.acme.dto;

public class FeedItemCheckRequestDTO {
	String title;
	String date;
	
	public FeedItemCheckRequestDTO() {
	}
	public FeedItemCheckRequestDTO(String title, String date) {
		super();
		this.title = title;
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
