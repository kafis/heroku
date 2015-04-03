package de.kafis.ouath2;

import de.kafis.ouath2.resources.OAuthCallbackResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class OAuth2Application extends Application<OAuth2Configuration> {

    public static void main(String[]args) throws Exception {
        new OAuth2Application().run(args);
    }
    @Override
    public void run(OAuth2Configuration oAuth2Configuration, Environment environment) throws Exception {

        environment.jersey().register(new OAuthCallbackResource());
    }
}
