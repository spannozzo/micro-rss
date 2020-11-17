package org.acme.repository;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.acme.entity.Feed;

@Singleton
@ActivateRequestContext
public class FeedRepository {

	@Inject
	EntityManager em;
	
	public Optional<List<Feed>> getFeeds() {
		List<Feed> results= Feed.list("deleted", false);
		
		if (results.size()==0) {
			return Optional.empty();
		}
		
		return Optional.of(results);
	}
	
	@Transactional
	public void save(Feed feed) {
		feed.deleted=false;
		
		feed.persist();
	}

	public Optional<Feed> findById(Long id) {
		
		return Feed.findByIdOptional(id);
	
	}

	@Transactional
	public Feed delete(Feed feed) {
		feed.deleted=true;
		
		em.merge(feed);
		
		return feed;
	}

	@Transactional
	public Feed edit(Feed feed) {
		
		em.merge(feed);
		
		return  feed;
	}
	
}
