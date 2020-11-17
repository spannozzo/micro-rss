package org.acme.dto;

import org.acme.entity.Feed;

public class FeedRequestDTO {

	String url;

	String name;

	String description;

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

	public static Feed getFeed(FeedRequestDTO feedRequestDTO) {
		Feed feed=new Feed(feedRequestDTO.getName(), feedRequestDTO.getUrl(), feedRequestDTO.getDescription(), false);
		return feed;
	}

}
