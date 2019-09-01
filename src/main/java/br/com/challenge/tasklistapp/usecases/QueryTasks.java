package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryTasks {

    @Autowired
    private TaskDataBaseGateway gateway;

    public List<Task> process(final String status) throws NotFoundException {

        if(status != null && !status.isEmpty()) {
            return gateway.findByStatus(status);
        }

        return gateway.findAll();
    }
}
