package de.kafis.ouath2.resources;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import de.kafis.ouath2.google.GoogleAuthorization;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/me/calendar")
public class CalendarResource {

    @Context
    private UriBuilder uriBuilder;

    @Context
    private UriInfo uriInfo;

    private GoogleAuthorization googleAuthorization;


    public CalendarResource(GoogleAuthorization googleAuthorization) {
        this.googleAuthorization = googleAuthorization;
    }

    @GET
    public Response calendar() {
        try {
            Credential credentials = googleAuthorization.getCredentialsFor("konrad");
            if(credentials == null) {
                return  googleAuthorization.redirectUserToGoogleLoginPage(
                        uriInfo.getAbsolutePathBuilder().path(OAuthCallbackResource.class).build().toString(), "konrad");
            }
            Events events = new Calendar(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credentials).events().list("").execute();
            return Response.ok(events.toPrettyString()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
