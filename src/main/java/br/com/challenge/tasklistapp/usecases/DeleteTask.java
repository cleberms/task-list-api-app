package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteTask {

    @Autowired
    private TaskDataBaseGateway gateway;

    public void process(final Long taskId) {
        gateway.deleteTaskById(taskId);
    }
}
