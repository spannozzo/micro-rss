package org.acme;

import java.time.Duration;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.dto.FeedItemDTO;
import org.acme.service.RSSFeedEmitterService;
import org.acme.service.RSSFeedService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.SseElementType;

import io.smallrye.mutiny.Multi;


@Path("/feeds")
@Produces(MediaType.APPLICATION_JSON)
public class RSSFeedEmitterController {
	
    static final Logger LOG = Logger.getLogger(RSSFeedEmitterController.class);

    @Inject
	RSSFeedService rSSFeedService;
    
    @Inject
	RSSFeedEmitterService rSSFeedEmitterService;
	
    @ConfigProperty(name = "feed-interval-minutes")
    int minutes;
    
    @ConfigProperty(name = "feed-interval-seconds")
    int seconds;
    
    @GET
    @Path("/stream/v1/{rssIndex}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<FeedItemDTO> streamFeedV4(@PathParam(value = "rssIndex") @NotBlank int id) {
       return Multi.createFrom().ticks()
    		   .every(Duration.ofSeconds(seconds))
    		   .onItem()
	   			.transformToMulti(tick->getFeed(id))
		   		.merge()
		   		.onItem().invoke(item->{
		   			rSSFeedEmitterService.send(item);
		   		})
		   		
    		   	;
    }
   
    Multi<FeedItemDTO> getFeed(int id) {
    	
    	List<FeedItemDTO> items=rSSFeedService.getFeeds(id);
    	
    	Multi<FeedItemDTO> multi=Multi.createFrom().items(items.stream());
    	
        return multi;
    }

	
}
