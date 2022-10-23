package com.rntgroup.reactive.programming.impl.service.impl;

import com.rntgroup.reactive.programming.impl.model.SportModel;
import com.rntgroup.reactive.programming.impl.repository.SportReactiveRepository;
import com.rntgroup.reactive.programming.impl.service.SportModelService;
import io.netty.util.internal.ThreadLocalRandom;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SportModelServiceImpl implements SportModelService {

    private final SportReactiveRepository sportReactiveRepository;

    public SportModelServiceImpl(SportReactiveRepository sportReactiveRepository) {
        this.sportReactiveRepository = sportReactiveRepository;
    }

    @Override
    public Mono<List<SportModel>> getAllSavedSPorts() {
        return sportReactiveRepository.findAll().collectList();
    }

    @Override
    public Mono<SportModel> addNewSport(String sportName) {
        var id = ThreadLocalRandom.current().nextLong();
        return sportReactiveRepository.save(new SportModel(id, sportName))
                .onErrorResume(throwable -> {
                    System.err.printf("The sport with name %s already exists", sportName);
                    return sportReactiveRepository.findByName(sportName);
                });
    }

    @Override
    public Mono<List<SportModel>> findSportsByNameLike(String nameKeyWord) {
        return sportReactiveRepository.findByNameContaining(nameKeyWord)
                .collectList();
    }

}
