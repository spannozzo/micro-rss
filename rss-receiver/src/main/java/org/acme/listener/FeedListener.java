package org.acme.listener;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.acme.dto.FeedItemCheckRequestDTO;
import org.acme.dto.FeedItemRequestDTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class FeedListener {

	public static Gson gson = new GsonBuilder().create();
	
	@Inject 
	@Channel("store-feed-item") 
	Emitter<FeedItemRequestDTO> storeFeedItemMessageEmitter;
		
	@ConfigProperty(name = "feed-crud-url")
	String feedGetUrl;

	static final Logger LOG = Logger.getLogger(FeedListener.class);

	@Incoming("feed-to-store")
	public CompletionStage<Void> processFeedToStore(Message<JsonObject> message) {

		return CompletableFuture.runAsync(() -> {

			LOG.info("BEGIN - kafka event for processing feed items");

			JsonObject payLoad = message.getPayload();

			storeFeedItemMessageEmitter.send(this.map(payLoad));
						
			LOG.info("END - kafka event for processing feed items");
			
		});

	}

	FeedItemRequestDTO map(JsonObject payLoad) {
		FeedItemRequestDTO dto = new FeedItemRequestDTO();
		dto.setTitle(payLoad.getString("title"));
		dto.setDate(payLoad.getString("date"));
		dto.setDescription(payLoad.getString("description"));
		dto.setImagePath(payLoad.getString("imagePath"));
		dto.setFeedRef(feedGetUrl + payLoad.getString("id"));

		return dto;
	}

}
