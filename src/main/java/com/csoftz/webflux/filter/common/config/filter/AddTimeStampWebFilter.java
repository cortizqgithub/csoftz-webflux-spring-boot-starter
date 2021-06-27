/*----------------------------------------------------------------------------*/
/* Source File:   ADDTIMESTAMPWEBFILTER.JAVA                                  */
/* Copyright (c), 2021 CSoftZ                                                 */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Jun.27/2021  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.csoftz.webflux.filter.common.config.filter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
 * <p>Adds a TimeStamp header to all responses.</p>
 *
 * @author ORTIC140 - Carlos Adolfo Ortiz Quirós
 * @version 1.1
 * @since 16 (JDK)
 */
@Slf4j
@Order(2)
public class AddTimeStampWebFilter implements WebFilter {
    private static final String X_TIME_STAMP_HDR = "X-time-stamp";
    private static final String PROCESSED_HEADER_INCLUDED = "Executing filter to add Timestamp of request being processed!!! Header included {}";

    /**
     * Adds a TimeStamp header to all responses.
     *
     * @param exchange Represents the web/client execution model.
     * @param chain    Connects to other filters in a row.
     * @return A Mono indicating it is a process which adds a header to client request.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.debug(PROCESSED_HEADER_INCLUDED, X_TIME_STAMP_HDR);
        exchange.getResponse()
            .getHeaders()
            .add(X_TIME_STAMP_HDR, LocalDateTime.now(ZoneOffset.UTC).toString());
        return chain.filter(exchange);
    }
}
