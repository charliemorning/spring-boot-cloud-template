package org.charlie.template.framework.configs.io.http;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.charlie.template.framework.configs.TemplateConfig;
import org.charlie.template.framework.constants.io.http.HttpConstants;
import org.charlie.template.framework.interceptors.httpclient.GlobalTraceIdHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * To configure apache http client.
 *
 * @author Charlie
 */
@Slf4j
@Configuration
@EnableScheduling
public class ApacheHttpClientConfig {

    private TemplateConfig templateConfig;

    @Autowired
    public void setTemplateConfig(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }


    /**
     * to config pooled connection manager
     * @return
     */
    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(HttpConstants.HTTP_CLIENT_CONN_MANAGER_TTL, TimeUnit.MILLISECONDS);
        poolingConnectionManager.setMaxTotal(HttpConstants.POOL_MANAGER_MAX_TOTAL_DEFAULT_CONNECTIONS);
        poolingConnectionManager.setDefaultMaxPerRoute(HttpConstants.POOL_MANAGER_MAX_ROUTE_DEFAULT_CONNECTIONS);
        poolingConnectionManager.setDefaultConnectionConfig(
                ConnectionConfig
                        .custom()
                        .setCharset(StandardCharsets.UTF_8)
                        .build()
        );

        // TODO: control max connection per route
//        HttpHost localhost = new HttpHost("http://localhost", 8080);
//        poolingConnectionManager.setMaxPerRoute(new HttpRoute(localhost), MAX_LOCALHOST_CONNECTIONS);
        return poolingConnectionManager;
    }

    /**
     * to define retry handler
     * @return
     */
    @Bean
    public HttpRequestRetryHandler httpRequestRetryHandler() {

        return (exception, executionCount, context) -> {
            if (executionCount >= HttpConstants.HTTP_CLIENT_RETRY) {
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                return true;
            }
            if (exception instanceof UnknownHostException) {
                return false;
            }
            if (exception instanceof SSLException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        };
    }

    /**
     * to define keepalive strategy
     * @return
     */
    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (httpResponse, httpContext) -> {
            // to find the timeout value in response header
            HeaderIterator headerIterator = httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE);
            HeaderElementIterator elementIterator = new BasicHeaderElementIterator(headerIterator);
            while (elementIterator.hasNext()) {
                HeaderElement element = elementIterator.nextElement();
                String param = element.getName();
                String value = element.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000; // convert to ms
                }
            }
            return HttpConstants.DEFAULT_KEEP_ALIVE_TIME;
        };
    }

    @Bean
    public Runnable idleConnectionMonitor(PoolingHttpClientConnectionManager pool) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = HttpConstants.IDLE_CONNECTION_CLOSE_INTERVAL * 1000)
            public void run() {
                if (pool != null) {
                    pool.closeExpiredConnections();
                    pool.closeIdleConnections(HttpConstants.IDLE_CONNECTION_WAIT_SECOND, TimeUnit.MILLISECONDS);
                }
            }
        };
    }

    @Bean
    public Runnable connectionMonitor(PoolingHttpClientConnectionManager pool) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = HttpConstants.CONNECTION_MONITOR_INTERVAL * 1000)
            public void run() {
                if (pool != null) {
                    log.info(String.valueOf(pool.getTotalStats()));
                }
            }
        };
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix(HttpConstants.IDLE_CONNECTION_MONITOR_THREAD_NAME);
        scheduler.setPoolSize(HttpConstants.IDLE_CONNECTION_MONITOR_THREAD_NUM);
        return scheduler;
    }


    /**
     * to define http client
     * @return
     */
    @Bean
    public CloseableHttpClient httpClient() {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(HttpConstants.CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(HttpConstants.REQUEST_TIMEOUT)
                .setSocketTimeout(HttpConstants.SOCKET_TIMEOUT)
                .build();

        return HttpClients.custom()
                .addInterceptorFirst(new GlobalTraceIdHttpRequestInterceptor())
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(httpRequestRetryHandler())
                .setConnectionManager(poolingHttpClientConnectionManager())
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .setConnectionManagerShared(true)
                .build();
    }

}

