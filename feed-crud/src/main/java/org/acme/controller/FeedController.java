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

import org.acme.dto.FeedDTO;
import org.acme.dto.FeedRequestDTO;
import org.acme.entity.Feed;
import org.acme.service.FeedService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@RequestScoped
@Path("/feeds")
public class FeedController {

	@Inject
	FeedService feedService;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "get list of feeds")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "List of feeds", content = {
			@Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = FeedDTO.class))

			}), 
			@APIResponse(responseCode = "404", description = "Couldn't find any feed", content = @Content)

	})
	public Response getFeeds() {
		
		return Response.status(Response.Status.OK).entity(FeedDTO.mapFeeds(feedService.getFeeds().orElseThrow())).build();
		
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "save a feed")
	@APIResponses(value = { @APIResponse(responseCode = "201", description = "Feed created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedDTO.class))

			}),
			@APIResponse(responseCode = "400", description = "Bad request", content = @Content)

	})
	public Response saveFeed(
			@Parameter(
		            description = "Json rapresentation of the feed to insert",
		            required = true,
		            schema = @Schema(implementation = FeedRequestDTO.class))
			
			@Valid FeedRequestDTO feedRequestDTO){

		
		Feed savedFeed=feedService.save(feedRequestDTO);
		
		return Response.status(Response.Status.CREATED).entity(FeedDTO.getDto(savedFeed)).build();

	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "get one feed from its id")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Feed rapresentation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedDTO.class))

			}),
			@APIResponse(responseCode = "404", description = "Couldn't find any feed", content = @Content)

	})
	public Response getFeed(
			@Parameter(
		            description = "ID of the feed",
		            required = true,
		            example = "123",
		            schema = @Schema(type = SchemaType.STRING))
			
			@PathParam(value = "id") @NotNull Long id) {
		
		return Response.status(Response.Status.OK).entity(FeedDTO.getDto(feedService.findById(id).orElseThrow())).build();
		
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "remove a feed")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Feed deleted", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedDTO.class))

			}),
			@APIResponse(responseCode = "400", description = "Bad request", content = @Content),
			@APIResponse(responseCode = "404", description = "Couldn't find feed to delete", content = @Content)

	})
	public Response deleteFeed(
			@Parameter(
		            description = "ID of the feed",
		            required = true,
		            example = "1",
		            schema = @Schema(type = SchemaType.STRING))
			
			@PathParam(value = "id") @Positive @NotNull Long id){

		
		return Response.status(Response.Status.OK).entity(FeedDTO.getDto(feedService.delete(id))).build();

	}

	@PATCH
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "edit a feed")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Feed modified", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeedDTO.class))

			}),
			@APIResponse(responseCode = "400", description = "Bad request", content = @Content),
			@APIResponse(responseCode = "304", description = "Nothing to Change", content = @Content),
			@APIResponse(responseCode = "404", description = "Couldn't find feed to edit", content = @Content)

	})
	public Response editFeed(
			
			@Parameter(
		            description = "Json rapresentation of the fields to modify",
		            required = true,
		            schema = @Schema(implementation = FeedDTO.class))
			
			@Valid FeedDTO editRequestDTO
			
			){


		return Response.status(Response.Status.OK).entity(FeedDTO.getDto(feedService.edit(editRequestDTO).orElseThrow())).build();

	}
	
	
}
