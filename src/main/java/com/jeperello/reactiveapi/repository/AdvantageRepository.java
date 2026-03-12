package com.jeperello.reactiveapi.repository;

import com.jeperello.reactiveapi.model.Advantage;
import com.jeperello.reactiveapi.model.Technology;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvantageRepository extends ReactiveCrudRepository<Advantage, Long> {}
