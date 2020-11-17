package org.acme.dto;

import java.time.LocalDateTime;

public class FeedItemDTO {

	String description;
	String title;
	String imagePath;
	LocalDateTime date;
	
	String id;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "FeedItem [title:" + title + "at:"+ date+"]";
	}

	public void setTitle(String title) {
		this.title=title;
		
	}
	public String getTitle() {
		return title;
	}

	public void setImagePath(String imagePath) {
		this.imagePath=imagePath;		
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setDate(LocalDateTime date) {
		this.date=date;
		
	}

	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * it forces the set to use equals when new element are added
	 */
	@Override
	public int hashCode() {
		return 0;
	}
	
	/**
	 * if feeds have same title and published date it will be considerate as duplicated
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FeedItemDTO) {
			if (title.equals(((FeedItemDTO) obj).getTitle()) && date.equals(date)) {
				return true;
			}
		}
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FeedItemDTO() {
	}
	
}
