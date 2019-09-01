package br.com.challenge.tasklistapp.gateways.mongo.repository;

import br.com.challenge.tasklistapp.domains.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter, String> {}
