package com.shavatech.management.infraestructure.websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;

import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@ApplicationScoped
public class ReconnectConnectionController {

    private static final Logger LOG = Logger.getLogger(ReconnectConnectionController.class.getName());

    @Inject
    WebSocketHandler webSocketHandler;

    @Retry(delay = 1,
        maxRetries = -1,
        delayUnit = ChronoUnit.MINUTES
    )
    public  void reconnect(){
        LOG.info("Reconectando Websocket Cliente");
        webSocketHandler.init();
    }
}
