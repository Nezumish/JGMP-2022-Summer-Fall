package com.rntgroup.big.data.task6.consumer.service;

import com.rntgroup.big.data.task6.domain.Bottle;

/**
 * Simple not even CRUD service to interact with Bottles
 */
public interface BottleService {

    /**
     * Saves the given Bottle object somewhere
     *
     * @param bottle - a Bottle object to save
     */
    void saveBottle(Bottle bottle);

}
