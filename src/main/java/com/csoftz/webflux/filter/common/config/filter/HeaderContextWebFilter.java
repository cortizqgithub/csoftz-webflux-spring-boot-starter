/*----------------------------------------------------------------------------*/
/* Source File:   HEADERCONTEXTWEBFILTER.JAVA                                 */
/* Copyright (c), 2021 CSoftZ                                                 */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Jun.27/2021  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.csoftz.webflux.filter.common.config.filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * <p>Web Filter in Reactive stack.
 * <a href="https://java-focus.com/pass-header-to-reactive-webclient-spring-webflux/">
 * Pass header to reactive webclient – Spring webflux</a></p>
 *
 * <p>This filter adds all request headers to all the WebClient requests.</p>
 *
 * @author COQ - Carlos Adolfo Ortiz Quirós
 * @version 1.1
 * @since 16 (JDK)
 */
@Order(1)
@Slf4j
public class HeaderContextWebFilter implements WebFilter {

    private static final String HEADERS = "headers";
    private static final String MSG_HEADERS_LIST = "HEADERS LIST";
    private static final String MSG_DELIMITER_START = "START============";
    private static final String MSG_DELIMITER_END = "============END";
    private static final String MSG_PRINT_HEADER = "HDR: {}:[{}]";
    private static final String HDR_MY_FAIRY_KING = "MyFairyKing";
    private static final String HDR_MY_FAIRY_KING_VALUE = "The Highest King";
    private static final String HDR_THE_LONGESTHDR = "TheLONGESTHDR";
    private static final String HDR_THE_LONGESTHDR_VALUE = "This is the longest header,  got it!";

    /**
     * Adds all request headers to be available to any WebClient.
     *
     * @param exchange Represents the web/client execution model.
     * @param chain    Connects to other filters in a row.
     * @return A Mono indicating it is a process which adds a header to client request.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info(MSG_HEADERS_LIST);
        log.info(MSG_DELIMITER_START);

        exchange.getRequest()
            .getHeaders()
            .toSingleValueMap()
            .forEach((key, value) -> log.info(MSG_PRINT_HEADER, key, value));
        log.info(MSG_DELIMITER_END);

        ServerWebExchange mutatedExchange = exchange
            .mutate()
            .request(exchange.getRequest()
                .mutate()
                .header(HDR_MY_FAIRY_KING, HDR_MY_FAIRY_KING_VALUE)
                .header(HDR_THE_LONGESTHDR, HDR_THE_LONGESTHDR_VALUE)
                .build())
            .build();

        log.info("HEADERS LIST AFTER");
        log.info(MSG_DELIMITER_START);
        mutatedExchange
            .getRequest()
            .getHeaders()
            .toSingleValueMap()
            .forEach((key, value) -> log.info(MSG_PRINT_HEADER, key, value));
        log.info(MSG_DELIMITER_END);

        return chain
            .filter(exchange)
            .contextWrite(ctx -> {
                ctx = ctx.put(HEADERS,
                    mutatedExchange.getRequest()
                        .getHeaders()
                        .toSingleValueMap()
                );
                return ctx;
            });
    }
}
