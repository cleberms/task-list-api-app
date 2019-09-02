package br.com.challenge.tasklistapp.gateways.mongo;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import br.com.challenge.tasklistapp.gateways.mongo.repository.TaskRepository;
import com.mongodb.MongoException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
public class TaskDataBaseGatewayImpl implements TaskDataBaseGateway {

    @Autowired
    private TaskRepository repository;
    
    private static final String TASK_ID = "TASK_ID";
    private static final String FEATURE = "FEATURE";
    private static final String SAVE = "SAVE";
    private static final String STATUS = "STATUS";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String EXCEPTION = "EXCEPTION";
    private static final String UPDATE = "UPDATE";
    private static final String FIND_ALL = "FIND_ALL";
    private static final String FIND_ALL_BY_STATUS = "FIND_ALL_BY_STATUS";
    private static final String FIND_BY_ID = "FIND_BY_ID";
    private static final String DELETE_BY_ID = "DELETE_BY_ID";
    private static final String TASKS_NOT_FOUND = "Tasks not found";

    public Task save(final Task task) {

        try {

            task.setCreatedAt(LocalDateTime.now());

            final Task taskSaved = repository.save(task);

            log.info("Task id = {} saved successfully", kv(TASK_ID, task.getTaskId()), kv(FEATURE, SAVE), kv(STATUS, SUCCESS));

            return taskSaved;

        } catch (Exception ex) {

            log.error("Error to save task", kv(FEATURE, SAVE), kv(STATUS, ERROR), kv(EXCEPTION, ex));

            throw new MongoException("Error to save task");
        }
    }

    public Task update(final Task task) throws NotFoundException {

        try {
            final Optional<Task> optionalTask = Optional.ofNullable(repository.findByTaskId(task.getTaskId()));

            if (optionalTask.isPresent()) {

                final Task savedTask = optionalTask.get();

                task.setUid(savedTask.getUid());
                task.setCreatedAt(savedTask.getCreatedAt());
                task.setUpdateAt(LocalDateTime.now());

                final Task taskSaved = repository.save(task);

                log.info("Task id = {} update successfully", kv(TASK_ID, task.getTaskId()), kv(FEATURE, UPDATE), kv(STATUS, SUCCESS));

                return taskSaved;
            }
        }catch (Exception ex) {

            log.error("Error to update task", kv(FEATURE, UPDATE), kv(STATUS, ERROR), kv(EXCEPTION, ex));

            throw new MongoException("Error to update task");
        }

        log.info(TASKS_NOT_FOUND, kv(FEATURE, UPDATE), kv(STATUS, SUCCESS));

        throw new NotFoundException(TASKS_NOT_FOUND);

    }

    public List<Task> findAll() throws NotFoundException {

        try {
            final Optional<List<Task>> optionalTasks = Optional.ofNullable(repository.findAll());

            if (optionalTasks.isPresent() && !optionalTasks.get().isEmpty()) {
                log.info("Tasks found successfully", kv(FEATURE, FIND_ALL), kv(STATUS, SUCCESS));

                return optionalTasks.get();
            }
        } catch (Exception ex) {

            log.error("Error to findAll tasks", kv(FEATURE, FIND_ALL), kv(STATUS, ERROR), kv(EXCEPTION, ex));

            throw new MongoException("Error to find all tasks");
        }

        log.info(TASKS_NOT_FOUND, kv(FEATURE, FIND_ALL), kv(STATUS, SUCCESS));

        throw new NotFoundException(TASKS_NOT_FOUND);

    }

    public List<Task> findByStatus(final String status) throws NotFoundException {

        try {
            final Optional<List<Task>> optionalTasks = Optional.ofNullable(repository.findByStatus(status));

            if (optionalTasks.isPresent()) {
                log.info("Tasks by status found successfully", kv(FEATURE, FIND_ALL_BY_STATUS), kv(STATUS, SUCCESS));

                return optionalTasks.get();
            }
        } catch (Exception ex) {

            log.error("Error to find tasks by status", kv(FEATURE, FIND_ALL_BY_STATUS), kv(STATUS, ERROR), kv(EXCEPTION, ex));

            throw new MongoException(String.format("Error to find tasks by status %s", status));
        }

        log.info("Any tasks found by status {}", kv("TASK_STATUS", status), kv(FEATURE, FIND_ALL_BY_STATUS), kv(STATUS, "NOT_FOUND"));

        throw new NotFoundException(String.format("Tasks with status %s not found", status));

    }

    public Task findById(final Long id)  throws NotFoundException {

        try{
            final Optional<Task> optionalTask = Optional.ofNullable(repository.findByTaskId(id));

            if(optionalTask.isPresent()) {
                log.info("Tasks id = {} found successfully", kv(TASK_ID, id), kv(FEATURE, FIND_BY_ID), kv(STATUS, SUCCESS));

                return optionalTask.get();
            }
        } catch (Exception ex) {
            log.error("Error to find tasks with id = {}", kv(TASK_ID, id), kv(FEATURE, FIND_BY_ID), kv(STATUS, ERROR), kv(EXCEPTION, ex));

            throw new MongoException(String.format("Error to find task with id = %s", id));
        }

        log.info("Any task found by id = {}", kv("TASK_STATUS", id), kv(FEATURE, FIND_BY_ID), kv(STATUS, "NOT_FOUND"));

        throw new NotFoundException(String.format("Task with id %s not found", id));
    }

    public void deleteTaskById(final Long id){

        try {
            repository.deleteByTaskId(id);
        } catch (Exception ex) {
            log.error("Error to delete task with id = {}", kv(TASK_ID, id), kv(FEATURE, DELETE_BY_ID), kv(STATUS, ERROR), kv(EXCEPTION, ex));

            throw new MongoException(String.format("Error to delete task with id = %s", id));
        }

        log.error("Task id = {} deleted successfully", kv(TASK_ID, id), kv(FEATURE, DELETE_BY_ID), kv(STATUS, SUCCESS));
    }

}
