package org.acme.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.acme.dto.FeedDTO;
import org.acme.dto.FeedRequestDTO;
import org.acme.entity.Feed;
import org.acme.repository.FeedRepository;


@Singleton
public class FeedService {

	@Inject
	FeedRepository repository;
	
	public Optional<List<Feed>> getFeeds() {
		return repository.getFeeds();
		
	}

	public Feed save(FeedRequestDTO feedRequestDTO) {
		
		Feed feed=FeedRequestDTO.getFeed(feedRequestDTO);
		
		repository.save(feed);
		
		return feed;
		
	}

	public Optional<Feed> findById(Long id) {
		
		return repository.findById(id);
	}

	
	public Optional<Feed> edit(FeedDTO editRequestDTO) {
		Feed feed=findById(Long.parseLong(editRequestDTO.getId())).orElseThrow();
		
		feed=editRequestDTO.merge(feed);
		
		if (feed.deleted) {
			return Optional.empty();
		}
		
		return Optional.of(feed);
	}

	public Feed delete(Long id) {
		
		Feed feed=findById(id).orElseThrow();
		
		return repository.delete(feed);
	}

	
	
}
