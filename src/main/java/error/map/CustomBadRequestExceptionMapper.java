package error.map;

import error.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomBadRequestExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), "400");
        return Response.ok(response)
                .status(400)
                .build();
    }
}
