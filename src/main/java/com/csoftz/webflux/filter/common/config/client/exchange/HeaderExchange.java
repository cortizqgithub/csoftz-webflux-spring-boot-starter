/*----------------------------------------------------------------------------*/
/* Source File:   HEADEREXCHANGE.JAVA                                         */
/* Copyright (c), 2021 CSoftZ                                                 */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Jun.27/2021  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.csoftz.webflux.filter.common.config.client.exchange;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;

import com.csoftz.webflux.filter.common.config.filter.HeaderContextWebFilter;

import reactor.core.publisher.Mono;

/**
 * Add headers to the context in a reactive chain.
 * <p><b>NOTE:</b> It is required that a web filter to populate the headers is
 * called before this exchange filter function is called. An example is shown
 * in the {@link HeaderContextWebFilter}, also pay special attention to the
 * Web filter ordering.
 * </p>
 *
 * @author COQ - Carlos Adolfo Ortiz Quirós
 * @version 1.1
 * @since 16 (JDK)
 */
@Component
public class HeaderExchange implements ExchangeFilterFunction {

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return Mono.deferContextual(Mono::just)
            .flatMap(context -> {
                LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
                Map<String, String> ctxMap = context.get("headers");
                ctxMap.forEach(valueMap::add);
                return next.exchange(
                    ClientRequest
                        .from(request)
                        .headers(httpHeaders -> httpHeaders.addAll(valueMap))
                        .build()
                );
            });
    }
}
