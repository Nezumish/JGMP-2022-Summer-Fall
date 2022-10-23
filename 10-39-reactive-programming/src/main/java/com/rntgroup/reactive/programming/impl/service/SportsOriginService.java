package com.rntgroup.reactive.programming.impl.service;

import com.rntgroup.reactive.programming.impl.model.SportModel;
import reactor.core.publisher.Flux;

public interface SportsOriginService {

    Flux<SportModel> getSportsInfo();

}
