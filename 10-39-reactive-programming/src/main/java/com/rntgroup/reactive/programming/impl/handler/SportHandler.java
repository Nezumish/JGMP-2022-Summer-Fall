package com.rntgroup.reactive.programming.impl.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rntgroup.reactive.programming.api.dto.SportDto;
import com.rntgroup.reactive.programming.api.v1.SportApi;
import com.rntgroup.reactive.programming.impl.service.SportModelService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SportHandler implements SportApi {

    private final ObjectMapper objectMapper;
    private final SportModelService sportModelService;

    public SportHandler(ObjectMapper objectMapper, SportModelService sportModelService) {
        this.objectMapper = objectMapper;
        this.sportModelService = sportModelService;
    }

    @Override
    public Mono<ServerResponse> getAllSports(ServerRequest serverRequest) {
        return sportModelService.getAllSavedSPorts()
                .map(sportModel -> objectMapper.convertValue(sportModel, SportDto[].class))
                .flatMap(sportDtos-> ServerResponse.ok().body(Mono.just(sportDtos), new ParameterizedTypeReference<>() {})
                        .switchIfEmpty(ServerResponse.notFound().build())
                );
    }

    @Override
    public Mono<ServerResponse> findSportsByNameLike(ServerRequest serverRequest) {
        var sportName = serverRequest.queryParam("q").get();
        return sportModelService.findSportsByNameLike(sportName)
                .map(sportModel -> objectMapper.convertValue(sportModel, SportDto[].class))
                .flatMap(sportDtos-> ServerResponse.ok().body(Mono.just(sportDtos), new ParameterizedTypeReference<>() {})
                        .switchIfEmpty(ServerResponse.notFound().build())
                );
    }

    @Override
    public Mono<ServerResponse> addSportWithName(ServerRequest serverRequest) {
        var sportName = serverRequest.pathVariable("sportname");
        return sportModelService.addNewSport(sportName)
                .map(sportModel -> objectMapper.convertValue(sportModel, SportDto.class))
                .flatMap(sportDto -> ServerResponse.ok().body(Mono.just(sportDto), SportDto.class));
    }

}
