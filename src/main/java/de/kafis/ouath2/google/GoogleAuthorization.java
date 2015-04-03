package de.kafis.ouath2.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

public class GoogleAuthorization {

    private GoogleAuthorizationCodeFlow flow;

    public GoogleAuthorization(String clientId, String clientSecret) {
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    clientId,
                    clientSecret,
                    Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(
                    new MemoryDataStoreFactory()).setAccessType("offline").build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Credential getCredentialsFor(String userId) {
        try {
            return flow.loadCredential(userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response redirectUserToGoogleLoginPage(String redirectUrl, String userId) throws Exception {
        return Response.seeOther(new URI(flow.newAuthorizationUrl().setRedirectUri(redirectUrl).build())).build();
    }

    public void exchangeAccessToken(String authorizationCode, String userId) {
        GoogleTokenResponse token = null;
        try {
            token = flow.newTokenRequest(authorizationCode).execute();
            flow.createAndStoreCredential(token,userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
