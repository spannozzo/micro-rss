package org.acme.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import org.acme.dto.FeedDTO;
import org.acme.dto.FeedItemDTO;
import org.acme.restclient.FeedRestClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Singleton
public class RSSFeedService {

	static final Logger LOG = Logger.getLogger(RSSFeedService.class);
	
	@Inject
	@RestClient
	FeedRestClient feedRestClient;
	
	public List<FeedItemDTO> getFeeds(long id) {
		
		String url="";
		
		try {
			Response restResponse=feedRestClient.getFeed(id);
			
			FeedDTO feedDto=restResponse.readEntity(FeedDTO.class);
			
			url=feedDto.getUrl();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		
		
		List<FeedItemDTO> list=null;
		try (CloseableHttpClient client = HttpClients.createMinimal()) {
	    	  HttpUriRequest request = new HttpGet(url);
	    	  try (CloseableHttpResponse response = client.execute(request);
	    	       InputStream stream = response.getEntity().getContent()) {
	    		  
	    	    SyndFeedInput input = new SyndFeedInput();
	    	    SyndFeed feed = input.build(new XmlReader(stream));
	    	    
    	    	list = feed.getEntries()
	    	    			.parallelStream()
	    	    			.map(item -> {
	    	    				FeedItemDTO fi=new FeedItemDTO();
	    	    				
	    	    				fi.setId(String.valueOf(id));
	    	    				
		    	    			fi.setDescription(item.getDescription().getValue());
		    	    			fi.setTitle(item.getTitle());
		    	    			    	    			
		    	    			fi.setDate(LocalDateTime.ofInstant(item.getPublishedDate().toInstant(),ZoneOffset.systemDefault()));
		    	    			    	    			
		    	    			if (item.getEnclosures().size()>0) {
		    	    				fi.setImagePath(item.getEnclosures().get(0).getUrl());
								}
		    	    			
		    	    			return fi;
	    	    		})
	    	    		.collect(Collectors.toList())
	    	    		
	    	    		;
	    	    
	    	  } catch (ClientProtocolException e) {
				LOG.error(e);
			} catch (IllegalArgumentException e) {
				LOG.error(e);
			} catch (FeedException e) {
				LOG.error(e);
			}
	    	} catch (IOException e) {
	    		LOG.error(e);
			}
		return list;
	}

}
