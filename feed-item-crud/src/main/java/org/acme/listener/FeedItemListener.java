package org.acme.listener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.dto.FeedItemCheckRequestDTO;
import org.acme.dto.FeedItemRequestDTO;
import org.acme.service.FeedItemService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class FeedItemListener {

	static final Logger LOG = Logger.getLogger(FeedItemListener.class);

	@Inject
	FeedItemService service;
		

	@Incoming("forwarded-item-to-store")
	public CompletionStage<Void> forwardedItemToStore(Message<JsonObject> message) {

		return CompletableFuture.runAsync(() -> {
			
			JsonObject payLoad = message.getPayload();
			
			FeedItemCheckRequestDTO itemCheckrequestDTO=this.mapFeedItemCheckRequestDTO(payLoad);
			
			if(service.findByTitleAndPublishedDate(itemCheckrequestDTO).isEmpty()) {
				FeedItemRequestDTO requestDTO=this.mapFeedItemRequestDTO(payLoad);
				
				LOG.info("new feed item to save: "+requestDTO.getTitle());

				service.save(requestDTO);
			}
			
		});

	}

	FeedItemRequestDTO mapFeedItemRequestDTO(JsonObject payLoad) {
		FeedItemRequestDTO dto = new FeedItemRequestDTO();
		dto.setTitle(payLoad.getString("title"));
		dto.setDate(payLoad.getString("date"));
		dto.setDescription(payLoad.getString("description"));
		dto.setImagePath(payLoad.getString("imagePath"));
		dto.setFeedRef(payLoad.getString("feedRef"));

		return dto;
	}
	FeedItemCheckRequestDTO mapFeedItemCheckRequestDTO(JsonObject payLoad) {
		FeedItemCheckRequestDTO dto=new FeedItemCheckRequestDTO();
		
		dto.setTitle(payLoad.getString("title"));
		dto.setDate(payLoad.getString("date"));
		
		return dto;
	}
}
