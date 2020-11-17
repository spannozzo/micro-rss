package org.acme.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.entity.Feed;
import org.acme.exceptions.NotModifiedException;

public class FeedDTO {

	String id;
	
	String url;
	
	String name;
	
	String description;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static List<FeedDTO> mapFeeds(List<?> feeds) {
		return feeds.stream()
					.map(feed ->getDto((Feed)feed))
					.collect(Collectors.toList())
		;
		
	}
	public static FeedDTO getDto(Feed x) {
		FeedDTO dto=new FeedDTO();
		
		dto.setId(String.valueOf(x.id));
		dto.setDescription(x.description);
		dto.setName(x.name);
		dto.setUrl(x.url);
		
		return dto;
	}
	
	
	public Feed merge(Feed feed) throws NotModifiedException {
		
		if (feed.name.equals(name) && feed.description.equals(description) && feed.url.equals(url)) {
			throw new NotModifiedException("Trying to modify feed "+id+" with its same values");
		}
		
		if (feed.name!=null) {
			feed.name=name;
		}
		if (feed.description!=null) {
			feed.description=description;
		}
		if (feed.url!=null) {
			feed.url=url;
		}

		return feed;
	}
	
	
	public FeedDTO() {
	}
}
