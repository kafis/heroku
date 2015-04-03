package de.kafis.ouath2;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;

public class OAuth2Configuration extends Configuration {
    @JsonProperty
    private ServerFactory server = new DefaultServerFactory();
    public OAuth2Configuration() {
        DefaultServerFactory factory = new DefaultServerFactory();
        factory.getApplicationConnectors().clear();
        String port = System.getenv("PORT");
        int defaultPort = 8080;
        if(port != null) {
             defaultPort = Integer.valueOf(port);
        }
        HttpConnectorFactory connector = new HttpConnectorFactory();
        connector.setPort(defaultPort);
        factory.getApplicationConnectors().add(connector);
        server = factory;

    }

    public ServerFactory getServer() {
        return server;
    }

    public void setServer(ServerFactory server) {
        this.server = server;
    }
}
