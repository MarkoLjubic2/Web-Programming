package resources;

import request.LoginRequest;
import services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

    @POST
    @Path("/register")
    @Produces({MediaType.APPLICATION_JSON})
    public Response register(@Valid LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();

        try {
            String jwt = this.userService.register(loginRequest.getUsername(), loginRequest.getPassword());
            response.put("message", "User registered successfully");
            response.put("jwt", jwt);
            return Response.ok(response).build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            response.put("message", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }
}
