package de.kafis.ouath2;

import de.kafis.ouath2.google.GoogleAuthorization;
import de.kafis.ouath2.resources.CalendarResource;
import de.kafis.ouath2.resources.OAuthCallbackResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class OAuth2Application extends Application<OAuth2Configuration> {

    public static void main(String[]args) throws Exception {
        new OAuth2Application().run(args);
    }
    @Override
    public void run(OAuth2Configuration oAuth2Configuration, Environment environment) throws Exception {
        GoogleAuthorization googleAuthorization = new GoogleAuthorization(System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET"));
        environment.jersey().register(new OAuthCallbackResource(googleAuthorization));
        environment.jersey().register(new CalendarResource(googleAuthorization));
    }
}
