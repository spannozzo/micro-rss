package org.acme.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.acme.entity.FeedItem;
import org.hibernate.Session;

@Singleton
@ActivateRequestContext
public class FeedItemRepository {

	@Inject
	EntityManager em;
	
	public Optional<List<FeedItem>> getItems() {
		List<FeedItem> results= FeedItem.listAll();
		
		if (results.size()==0) {
			return Optional.empty();
		}
		
		return Optional.of(results);
	}

	@Transactional
	public void save(FeedItem feedItem) {
		
		feedItem.persist();
		
	}

	public Optional<FeedItem> findById(Long id) {
		
		return FeedItem.findByIdOptional(id);
	}

	@Transactional
	public FeedItem edit(FeedItem feedItem) {
		em.merge(feedItem);
		
		return feedItem;
	}

	@Transactional
	public FeedItem delete(FeedItem feedItem) {
	
		feedItem.delete();
		
		return  feedItem;
	}

	public Optional<FeedItem> findByTitleAndPublishedDate(String title, String date) {
		
		
		Session session=em.unwrap(Session.class);
		
		Query query = session.getNamedQuery("findItemByTitleAndPublishedDate");
		
		query.setParameter("title", title);
		query.setParameter("date",LocalDateTime.parse(date));
		
		List<FeedItem> items=(List<FeedItem>)query.getResultList();
		
		if (items!=null && !items.isEmpty()) {
			return Optional.of(items.get(0));
		}
		
		return Optional.empty();
	}
}
