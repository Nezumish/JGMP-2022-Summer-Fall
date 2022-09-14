package com.rntgroup.messaging.in.java.impl.repository;

import com.rntgroup.messaging.in.java.impl.model.EventModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<EventModel, Long> {
}
