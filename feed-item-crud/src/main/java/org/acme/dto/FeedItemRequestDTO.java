package org.acme.dto;

import java.time.LocalDateTime;

import org.acme.entity.FeedItem;

public class FeedItemRequestDTO {
	
	
	String description;
	String title;
	String imagePath;
	String date;
	
	String feedRef;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getFeedRef() {
		return feedRef;
	}
	public void setFeedRef(String feedRef) {
		this.feedRef = feedRef;
	}
	
	
	public static FeedItem getFeedItem(FeedItemRequestDTO dto) {
		FeedItem item=new FeedItem();
		
		item.title=dto.getTitle();
		item.date=LocalDateTime.parse(dto.getDate());
		item.description=dto.getDescription();
		item.imagePath=dto.getImagePath();
		item.feedRef=dto.getFeedRef();
		
		return item;
	}
	
	
}
