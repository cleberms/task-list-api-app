package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import br.com.challenge.tasklistapp.usecases.mapper.TaskMergeMappers.TaskMergeMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateTask {

    @Autowired
    private TaskDataBaseGateway gateway;

    public Task process(final Task task, final Long taskId) throws NotFoundException {

        task.setTaskId(taskId);

        return gateway.update(task);
    }

    public Task processPatch(final Task task, final Long taskId) throws NotFoundException {
        final Task savedTask = gateway.findById(taskId);

        final Task taskToUpdate = TaskMergeMapper.MAPPERS.mergerTask(task, savedTask);

        return gateway.update(taskToUpdate);
    }
}
