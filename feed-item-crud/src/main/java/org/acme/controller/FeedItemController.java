package org.acme.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.dto.FeedItemCheckRequestDTO;
import org.acme.dto.FeedItemDTO;
import org.acme.dto.FeedItemRequestDTO;
import org.acme.entity.FeedItem;
import org.acme.service.FeedItemService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@RequestScoped
@Path("/feeds/items")
public class FeedItemController {

	@Inject
	FeedItemService feedItemService;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "get list of items")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "List of feed items", content = {
			@Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = FeedItemDTO.class))

			}), 
			@APIResponse(responseCode = "404", description = "Couldn't find any item", content = @Content)

	})
	public Response getFeeds() {
		
		return Response.status(Response.Status.OK).entity(FeedItemDTO.mapFeedItems(feedItemService.getFeedItems().orElseThrow())).build();
		
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "save a feed item")
	@APIResponses(value = { @APIResponse(responseCode = "201", description = "Feed item created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedItemDTO.class))

			}),
			@APIResponse(responseCode = "400", description = "Bad request", content = @Content)

	})
	public Response saveFeed(
			@Parameter(
		            description = "Json rapresentation of the feed item to insert",
		            required = true,
		            schema = @Schema(implementation = FeedItemRequestDTO.class))
			
			@Valid FeedItemRequestDTO feedItemRequestDTO){

		
		FeedItem savedItem=feedItemService.save(feedItemRequestDTO);
		
		return Response.status(Response.Status.CREATED).entity(FeedItemDTO.getDto(savedItem)).build();

	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "get one feed item from its id")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Feed item rapresentation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedItemDTO.class))

			}),
			@APIResponse(responseCode = "404", description = "Couldn't find any feed item", content = @Content)

	})
	public Response getFeed(
			@Parameter(
		            description = "ID of the feed item",
		            required = true,
		            example = "123",
		            schema = @Schema(type = SchemaType.STRING))
			
			@PathParam(value = "id") @NotNull Long id) {
		
		return Response.status(Response.Status.OK).entity(FeedItemDTO.getDto(feedItemService.findById(id).orElseThrow())).build();
		
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "remove an item")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Item deleted", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedItemDTO.class))

			}),
			@APIResponse(responseCode = "400", description = "Bad request", content = @Content),
			@APIResponse(responseCode = "404", description = "Couldn't find feed item to delete", content = @Content)

	})
	public Response deleteFeed(
			@Parameter(
		            description = "ID of the feed item",
		            required = true,
		            example = "1",
		            schema = @Schema(type = SchemaType.STRING))
			
			@PathParam(value = "id") @Positive @NotNull Long id){

		
		return Response.status(Response.Status.OK).entity(FeedItemDTO.getDto(feedItemService.delete(id))).build();

	}

	@PATCH
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "edit an item")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Item modified", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedItemDTO.class))

			}), 
			@APIResponse(responseCode = "400", description = "Bad request", content = @Content),
			@APIResponse(responseCode = "304", description = "Nothing to Change", content = @Content),
			@APIResponse(responseCode = "404", description = "Couldn't find feed item to edit", content = @Content)

	})
	public Response editItem(
			
			@Parameter(
		            description = "Json rapresentation of the fields to modify",
		            required = true,
		            schema = @Schema(implementation = FeedItemDTO.class))
			
			@Valid FeedItemDTO editRequestDTO
			
			){


		return Response.status(Response.Status.OK).entity(FeedItemDTO.getDto(feedItemService.edit(editRequestDTO))).build();

	}
	
	@POST
	@Path("/check/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "check a feed by title and date")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Feed item found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedItemDTO.class))

			}),
			@APIResponse(responseCode = "400", description = "Bad request", content = @Content),
			@APIResponse(responseCode = "404", description = "Feed item not found", content = @Content)

	})
	public Response checkFeedByTitleAndPublishedDate(
			@Parameter(
		            description = "Json rapresentation of the feed item to check",
		            required = true,
		            schema = @Schema(implementation = FeedItemCheckRequestDTO.class))
			
			@Valid FeedItemCheckRequestDTO feedItemCheckRequestDTO){

				
		return Response.status(Response.Status.OK).entity(FeedItemDTO.getDto(feedItemService.findByTitleAndPublishedDate(feedItemCheckRequestDTO).orElseThrow())).build();

	}
}
