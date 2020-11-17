package org.acme.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.acme.entity.FeedItem;
import org.acme.exceptions.NotModifiedException;

public class FeedItemDTO {

	
	String description;
	String title;
	String imagePath;
	LocalDateTime date;
	
	String id;

	String feedRef;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public String getFeedRef() {
		return feedRef;
	}

	public void setFeedRef(String feedRef) {
		this.feedRef = feedRef;
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


	public static List<FeedItemDTO> mapFeedItems(List<FeedItem> items) {
		return items.stream().map(fi->getDto(fi)).collect(Collectors.toList());
		
	}

	public static FeedItemDTO getDto(FeedItem item) {
		
		FeedItemDTO dto=new FeedItemDTO();
		
		dto.setId(item.id.toString());
		dto.setTitle(item.title);
		dto.setImagePath(item.imagePath);
		dto.setDescription(item.description);
		dto.setDate(item.date);
		
		return dto;
	}

	public FeedItem merge(FeedItem feedItem) {
		if (feedItem.title.equals(title) 
				&& feedItem.description.equals(description) 
				&& feedItem.date.equals(date)
				&& feedItem.imagePath.equals(imagePath)
				&& feedItem.feedRef.equals(feedRef)
			){
			throw new NotModifiedException("Trying to modify item "+id+" with its same values");
		}
		if (feedItem.title!=null) {
			feedItem.title=title;
		}
		if (feedItem.description!=null) {
			feedItem.description=description;
		}
		if (feedItem.date!=null) {
			feedItem.date=date;
		}
		if (feedItem.imagePath!=null) {
			feedItem.imagePath=imagePath;
		}
		if (feedItem.feedRef!=null) {
			feedItem.feedRef=feedRef;
		}
		
		return feedItem;
	}

	
	
}
