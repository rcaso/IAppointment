package com.shavatech.management.infraestructure.websocket;

import io.quarkus.runtime.Startup;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.quarkus.websockets.next.WebSocketConnector;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.util.logging.Logger;

@Startup
@ApplicationScoped
public class WebSocketHandler {

    private static final Logger LOG = Logger.getLogger(WebSocketHandler.class.getName());

    @ConfigProperty(name = "apointmentweb.uri")
    URI myUri;

    @Inject
    WebSocketConnector<JsonWebsocketClient> connector;


    @PostConstruct
    public void init(){
        LOG.info("init websocket client startup");
        WebSocketClientConnection connection = connector
                .baseUri(myUri)
                .connectAndAwait();
    }
}
