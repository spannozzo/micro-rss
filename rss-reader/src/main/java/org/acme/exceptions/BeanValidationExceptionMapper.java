package org.acme.exceptions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import static javax.ws.rs.core.Response.status;

/**
 * 
 * intercept the validation exceptions in order to send a message, 
 * I used also as global entry point for handling 500 and 304 error messages
 *
 */
@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ValidationException> {
	
    static final Logger LOG = Logger.getLogger(BeanValidationExceptionMapper.class);
    
	@Override
	public Response toResponse(ValidationException exception) {
		if (exception instanceof ConstraintViolationException) {
			return manageConstraintViolations((ConstraintViolationException) exception);
		}
						
		LOG.fatal("Internal Server Error. "+exception.getMessage() );
		
		return status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
	}
	
	
    Response manageConstraintViolations(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getConstraintViolations()
                .forEach(v -> 
                    errors.put(lastFieldName(v.getPropertyPath().iterator()), v.getMessage())
                );
        
        return status(Response.Status.BAD_REQUEST).entity(errors).build();
    }
    String lastFieldName(@NotNull Iterator<Path.Node> nodes) {
        Path.Node last = null;
        while (nodes.hasNext()) {
        	
            last = nodes.next();
        }
        if (last!=null) {
        	return last.getName();
		}
        return "";
    }
	
}
