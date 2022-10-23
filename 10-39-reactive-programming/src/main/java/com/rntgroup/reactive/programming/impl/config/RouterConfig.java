package com.rntgroup.reactive.programming.impl.config;

import com.rntgroup.reactive.programming.api.v1.SportApi;
import com.rntgroup.reactive.programming.api.v1.SportApiConstantsV1;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> router(SportApi sportApi) {
        return RouterFunctions
                .route(
                        GET(SportApiConstantsV1.ALL_SPORTS),
                        sportApi::getAllSports
                )
                .andRoute(
                        GET(SportApiConstantsV1.FIND_SPORTS_BY_NAME),
                        sportApi::findSportsByNameLike
                )
                .andRoute(
                        POST(SportApiConstantsV1.ADD_NEW_SPORT_WITH_NAME),
                        sportApi::addSportWithName
                );
    }

}
