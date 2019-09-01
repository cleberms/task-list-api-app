package br.com.challenge.tasklistapp.gateways;

import br.com.challenge.tasklistapp.domains.Task;
import javassist.NotFoundException;

import java.util.List;

public interface TaskDataBaseGateway {

    Task save(final Task task);

    Task update(final Task task) throws NotFoundException;

    List<Task> findAll() throws NotFoundException;

    List<Task> findByStatus(final String status) throws NotFoundException;

    Task findById(final Long id) throws NotFoundException;

    void deleteTaskById(final Long id);
}
