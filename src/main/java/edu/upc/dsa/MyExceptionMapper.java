package edu.upc.dsa;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception>{
    @Override
    public Response toResponse(Exception ex) {
        return Response.status(500).entity(ex.getMessage()).type("text/plain").build();
    }
}
