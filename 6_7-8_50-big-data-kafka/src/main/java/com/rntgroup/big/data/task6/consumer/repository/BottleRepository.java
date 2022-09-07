package com.rntgroup.big.data.task6.consumer.repository;

import com.rntgroup.big.data.task6.domain.Bottle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BottleRepository extends MongoRepository<Bottle, String> {
}
