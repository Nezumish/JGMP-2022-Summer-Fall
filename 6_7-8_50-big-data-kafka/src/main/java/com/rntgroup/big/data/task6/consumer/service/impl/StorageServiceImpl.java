package com.rntgroup.big.data.task6.consumer.service.impl;

import com.rntgroup.big.data.task6.consumer.repository.BottleRepository;
import com.rntgroup.big.data.task6.consumer.service.StorageService;
import com.rntgroup.big.data.task6.domain.Bottle;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {

    private final BottleRepository bottleRepository;

    public StorageServiceImpl(BottleRepository bottleRepository) {
        this.bottleRepository = bottleRepository;
    }

    @Override
    public void saveBottle(Bottle bottle) {
        var savedBottle = bottleRepository.save(bottle);
        System.out.println("Saved Bottle " + savedBottle.getId() + " to Mongo");
    }

}
