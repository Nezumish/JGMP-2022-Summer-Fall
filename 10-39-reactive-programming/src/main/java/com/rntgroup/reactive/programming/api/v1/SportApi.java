package com.rntgroup.reactive.programming.api.v1;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface SportApi {

    Mono<ServerResponse> getAllSports(ServerRequest serverRequest);

    Mono<ServerResponse> findSportsByNameLike(ServerRequest serverRequest);

    Mono<ServerResponse> addSportWithName(ServerRequest serverRequest);

}
