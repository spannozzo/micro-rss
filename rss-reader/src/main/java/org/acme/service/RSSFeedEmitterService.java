package org.acme.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.acme.dto.FeedItemDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;



@Singleton
public class RSSFeedEmitterService {
	@Inject 
	@Channel("feed-parsed") 
	Emitter<FeedItemDTO> messageEmitter;

	public void send(FeedItemDTO item) {
		messageEmitter.send(item);
		
	}
}
	