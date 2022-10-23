package com.rntgroup.reactive.programming.impl.repository;

import com.rntgroup.reactive.programming.impl.model.SportModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SportReactiveRepository extends R2dbcRepository<SportModel, Long> {

    Flux<SportModel> findByNameContaining(String name);

    Mono<SportModel> findByName(String name);

}
