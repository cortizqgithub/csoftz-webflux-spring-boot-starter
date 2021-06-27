/*----------------------------------------------------------------------------*/
/* Source File:   WEBFLUXSTARTERAUTOCONFIGURE.JAVA                            */
/* Copyright (c), 2021 CSoftZ                                                 */
/*----------------------------------------------------------------------------*/
/*-----------------------------------------------------------------------------
 History
 Jun.27/2021  COQ  File created.
 -----------------------------------------------------------------------------*/
package com.csoftz.webflux.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.csoftz.webflux.filter.common.config.filter.AddResponseHeaderWebFilter;
import com.csoftz.webflux.filter.common.config.filter.AddTimeStampWebFilter;
import com.csoftz.webflux.filter.common.config.filter.HeaderContextWebFilter;

/**
 * Configure the web filters for the WebFlux Stack in order to inject all headers to downstream services.
 *
 * @author COQ - Carlos Adolfo Ortiz Quirós
 * @version 1.1
 * @since 16 (JDK)
 */
@Configuration
public class WebFluxStarterAutoconfigure {

    /**
     * Web Filter responsible of retrieving all request headers and save it to the
     * Reactive stack.
     * @return Instance of the headers retrieval.
     * @see HeaderContextWebFilter
     */
    @Bean
    public HeaderContextWebFilter headerContextFilter() {
        return new HeaderContextWebFilter();
    }

    @Bean
    public AddTimeStampWebFilter addTimeStampWebFilter() {
        return new AddTimeStampWebFilter();
    }

    @Bean
    public AddResponseHeaderWebFilter addResponseHeaderWebFilter() {
        return new AddResponseHeaderWebFilter();
    }
}
