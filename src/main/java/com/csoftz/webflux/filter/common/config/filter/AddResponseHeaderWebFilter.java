/*----------------------------------------------------------------------------*/
/* Source File:   ADDRESPONSEHEADER.JAVA                                      */
/* Copyright (c), 2021 CSoftZ                                                 */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Jun.27/2021  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.csoftz.webflux.filter.common.config.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * <p>Web Filter in Reactive stack.
 * <a href="https://www.baeldung.com/spring-response-header">
 * How to Set a Header on a Response with Spring 5</a></p>
 *
 * <p>This filter adds the X-all-media to all responses.</p>
 *
 * @author COQ - Carlos Adolfo Ortiz Quirós
 * @version 1.1
 * @since 16 (JDK)
 */
@Slf4j
@Order(3)
public class AddResponseHeaderWebFilter implements WebFilter {

    private static final String X_ALL_MEDIA_HDR = "X-all-media";
    private static final String X_COMPONENT_HDR = "X-component";
    private static final String X_ALL_MEDIA_HDR_VALUE = "All Media";
    private static final String X_COMPONENT_HDR_VALUE = "DemoLIB Component";
    private static final String EXECUTING_FILTER_TO_ADD = "Executing filter to add {}";

    /**
     * Adds the 'X-all-media' header key/value to the Response to caller.
     *
     * @param exchange Represents the web/client execution model.
     * @param chain    Connects to other filters in a row.
     * @return A Mono indicating it is a process which adds a header to client request.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.debug(EXECUTING_FILTER_TO_ADD, X_ALL_MEDIA_HDR);

        var resHeaders = exchange.getResponse().getHeaders();
        resHeaders.add(X_ALL_MEDIA_HDR, X_ALL_MEDIA_HDR_VALUE);
        resHeaders.add(X_COMPONENT_HDR, X_COMPONENT_HDR_VALUE);
        return chain.filter(exchange);
    }
}
