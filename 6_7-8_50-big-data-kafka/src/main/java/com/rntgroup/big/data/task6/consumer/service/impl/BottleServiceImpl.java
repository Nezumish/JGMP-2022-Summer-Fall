package com.rntgroup.big.data.task6.consumer.service.impl;

import com.rntgroup.big.data.task6.consumer.repository.BottleRepository;
import com.rntgroup.big.data.task6.consumer.service.BottleService;
import com.rntgroup.big.data.task6.domain.Bottle;
import org.springframework.stereotype.Service;

/**
 * The simple implementation of {@link BottleService} working with MongoDB
 */
@Service
public class BottleServiceImpl implements BottleService {

    private final BottleRepository bottleRepository;

    public BottleServiceImpl(BottleRepository bottleRepository) {
        this.bottleRepository = bottleRepository;
    }

    /**
     * Saves the given Bottle object to MongoDB
     *
     * @param bottle - a Bottle object to save
     */
    @Override
    public void saveBottle(Bottle bottle) {
        var savedBottle = bottleRepository.save(bottle);
        System.out.println("Saved Bottle " + savedBottle.getId() + " to Mongo");
    }

}
