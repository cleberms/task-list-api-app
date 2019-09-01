package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.gateways.CounterDataBaseGateway;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTask {

    @Autowired
    private TaskDataBaseGateway taskGateway;

    @Autowired
    private CounterDataBaseGateway counterGateway;

    public Task process(final Task task) {

        task.setTaskId(getTaskId());
        task.setStatus(TaskStatus.TO_DO);

        return taskGateway.save(task);
    }

    private Long getTaskId() {

        return counterGateway.getNextTaskSequence();
    }
}
