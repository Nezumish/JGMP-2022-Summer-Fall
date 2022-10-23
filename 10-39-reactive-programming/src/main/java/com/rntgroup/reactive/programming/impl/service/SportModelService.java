package com.rntgroup.reactive.programming.impl.service;

import com.rntgroup.reactive.programming.impl.model.SportModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SportModelService {

    Mono<List<SportModel>> getAllSavedSPorts();

    Mono<SportModel> addNewSport(String sportName);

    Mono<List<SportModel>> findSportsByNameLike(String nameKeyWord);

}
