package org.charlie.example.framework.startups.failure;

import org.charlie.example.framework.exceptions.HttpConnectionsConfigureException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class HttpConnectionsConfigureFailureAnalyzer
        extends AbstractFailureAnalyzer<HttpConnectionsConfigureException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure,
                                      HttpConnectionsConfigureException cause) {
        return new FailureAnalysis(getDescription(cause), getAction(cause), cause);
    }

    private String getDescription(HttpConnectionsConfigureException ex) {
        return String.format("The sum of connections of each connection is %d, which is greater than maximum connections in pool manager, %d.", ex.getPoolMaxConnections(), ex.getSumRouteConnections());
    }

    private String getAction(HttpConnectionsConfigureException ex) {
        return String.format("Make sure max pool connections is greater than sum of connections of each route in classpath:application.yml.");
    }
}