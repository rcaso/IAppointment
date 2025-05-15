package com.shavatech.domain.management.control;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.websockets.next.Connection;
import io.quarkus.websockets.next.OpenClientConnections;
import io.quarkus.websockets.next.OpenConnections;
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

    @Inject
    OpenClientConnections connections;

    @Retry(delay = 1,
        maxRetries = 10,
        delayUnit = ChronoUnit.MINUTES
    )
    public  void reconnect(){
        LOG.info("Reconectando Websocket Cliente");
        webSocketHandler.init();
    }

    @Scheduled(every = "300s", delay = 2)
    public void checkConnection(){
        LOG.info("Verificando si hay conexiones activas");
        var connectionOpen = connections.stream().filter(Connection::isOpen).findFirst();
        if(connectionOpen.isEmpty()){
            LOG.info("no se encuentra conectado al servidor se reintentara conectar");
            webSocketHandler.init();
        }

    }
}
