package org.charlie.example.common.exceptions;


import lombok.Data;

@Data
public class HttpConnectionsConfigureException extends Exception {

    private int sumRouteConnections;

    private int poolMaxConnections;

    public HttpConnectionsConfigureException(String message, int sumRouteConnections, int poolMaxConnections) {
        super(message);
        this.sumRouteConnections = sumRouteConnections;
        this.poolMaxConnections = poolMaxConnections;
    }
}
