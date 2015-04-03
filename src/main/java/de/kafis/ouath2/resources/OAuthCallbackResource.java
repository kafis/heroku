package de.kafis.ouath2.resources;

import de.kafis.ouath2.google.GoogleAuthorization;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/oauth/callback")
public class OAuthCallbackResource {

    @Context
    private UriBuilder uriBuilder;
    @Context
    private UriInfo uriInfo;

    private GoogleAuthorization googleAuthorization;

    public OAuthCallbackResource(GoogleAuthorization googleAuthorization) {
        this.googleAuthorization = googleAuthorization;
    }


    @GET
    public Response callback(@QueryParam("code") String authorizationCode) {
        googleAuthorization.exchangeAccessToken(authorizationCode, "konrad", uriInfo.getBaseUriBuilder().path(OAuthCallbackResource.class).build().toString());
        return Response.seeOther(UriBuilder.fromResource(CalendarResource.class).build()).build();
    }
}
