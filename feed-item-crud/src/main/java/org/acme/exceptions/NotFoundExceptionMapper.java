package org.acme.exceptions;

import static javax.ws.rs.core.Response.status;

import java.util.NoSuchElementException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

/**
 * 
 * intercept the validation exceptions in order to send a message, 
 * I used also as global entry point for handling 500 and 304 error messages
 *
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NoSuchElementException> {
	
    static final Logger LOG = Logger.getLogger(NotFoundExceptionMapper.class);
    
	@Override
	public Response toResponse(NoSuchElementException exception) {
		return status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
	}
	
}
