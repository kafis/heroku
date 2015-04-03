package de.kafis.ouath2.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/oauth/callback")
public class OAuthCallbackResource {

    @GET
    public Response ping() {
        return Response.ok("pong").build();
    }
}
