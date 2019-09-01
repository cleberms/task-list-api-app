package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryTaskById {

    @Autowired
    private TaskDataBaseGateway gateway;

    public Task process(final Long taskId) throws NotFoundException {

        return gateway.findById(taskId);
    }
}
