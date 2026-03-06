package com.jeperello.reactiveapi.repository;

import com.jeperello.reactiveapi.model.Technology;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends ReactiveCrudRepository<Technology, Long> {}
