package org.acme.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import org.acme.dto.FeedItemCheckRequestDTO;
import org.acme.dto.FeedItemDTO;
import org.acme.dto.FeedItemRequestDTO;
import org.acme.entity.FeedItem;
import org.acme.repository.FeedItemRepository;

@Singleton
public class FeedItemService {

	@Inject
	FeedItemRepository repository;
	
	public Optional<List<FeedItem>> getFeedItems() {
		
		return repository.getItems();
	}

	@Transactional
	public FeedItem save(FeedItemRequestDTO feedItemRequestDTO) {
		FeedItem feedItem=FeedItemRequestDTO.getFeedItem(feedItemRequestDTO);
		
		repository.save(feedItem);
		
		return feedItem;
	}

	public Optional<FeedItem> findById(Long id) {
		return repository.findById(id);	
	}
	
	@Transactional
	public FeedItem edit(FeedItemDTO editRequestDTO) {
		FeedItem feedItem=findById(Long.parseLong(editRequestDTO.getId())).orElseThrow();
		
		feedItem=editRequestDTO.merge(feedItem);
		
		return repository.edit(feedItem);
	}

	@Transactional
	public FeedItem delete(Long id) {
		
		FeedItem feedItem=this.findById(id).orElseThrow();
		
		feedItem.delete();
		
		return feedItem;
	}

	public Optional<FeedItem> findByTitleAndPublishedDate(FeedItemCheckRequestDTO dto) {
		return repository.findByTitleAndPublishedDate(dto.getTitle(),dto.getDate());
	}

}
