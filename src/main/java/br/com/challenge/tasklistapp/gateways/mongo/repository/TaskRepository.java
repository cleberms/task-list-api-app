package br.com.challenge.tasklistapp.gateways.mongo.repository;

import br.com.challenge.tasklistapp.domains.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, Long> {

    List<Task> findByStatus(final String status);

    Task findByTaskId(final Long id);

    void deleteByTaskId(final Long Id);
}
