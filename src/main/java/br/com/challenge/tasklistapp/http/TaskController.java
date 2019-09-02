package br.com.challenge.tasklistapp.http;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.http.json.TaskVO;
import br.com.challenge.tasklistapp.http.json.TaskVORequest;
import br.com.challenge.tasklistapp.http.json.mapper.TaskMappers.TaskMapper;
import br.com.challenge.tasklistapp.usecases.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@Api(tags = "Task Management", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    @Autowired
    private QueryTasks queryTasks;

    @Autowired
    private QueryTaskById queryTaskById;

    @Autowired
    private CreateTask createTask;

    @Autowired
    private UpdateTask updateTask;

    @Autowired
    private DeleteTask deleteTask;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to create a new task", response = TaskVO.class)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskVO> createTask(
            @RequestBody @Valid final TaskVORequest request) {

        log.info("Request to create task", kv("API", "CREATE_TASK"));

        Task task = createTask.process(buildTask(request));

        return new ResponseEntity<>(buildResponse(task), HttpStatus.CREATED);

    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get tasks", response = TaskVO.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskVO>> getAllTasks(
            @ApiParam(allowableValues = "TO_DO, WIP, DONE", allowMultiple = true, allowEmptyValue = true)
            @RequestParam(value = "status", required = false) String status) throws NotFoundException {

        log.info("Request to get all tasks", kv("API", "GET_ALL_TASKS"));

        List<Task> taskList = queryTasks.process(status);

        return new ResponseEntity<>(buildListResponse(taskList), HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get task by id", response = TaskVO.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskVO> getTask(
            @ApiParam()
            @PathVariable("id") final String taskId ) throws NotFoundException {

        log.info("Request to get task by id", kv("API", "GET_TASK_ID"));

        Task task = queryTaskById.process(Long.parseLong(taskId));

        return new ResponseEntity<>(buildResponse(task), HttpStatus.OK);

    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to update task", response = TaskVO.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskVO> updateTask(
            @ApiParam()
            @PathVariable("id") final String taskId,
            @RequestBody @Valid final TaskVORequest request) throws NotFoundException {

        log.info("Request to update task by id", kv("API", "UPDATE_TASK_ID"));

        Task task = updateTask.process(buildTask(request), Long.parseLong(taskId));

        return new ResponseEntity<>(buildResponse(task), HttpStatus.OK);

    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to update task", response = TaskVO.class)
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public ResponseEntity<TaskVO> updatePatchTask(
            @ApiParam()
            @PathVariable("id") final String taskId,
            @RequestBody final TaskVORequest request) throws NotFoundException {

        log.info("Request to patch task by id", kv("API", "PATCH_TASK_ID"));

        Task task = updateTask.processPatch(buildTask(request), Long.parseLong(taskId));

        return new ResponseEntity<>(buildResponse(task), HttpStatus.PARTIAL_CONTENT);

    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to update task", response = TaskVO.class)
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public ResponseEntity deleteTask(
            @ApiParam()
            @PathVariable("id") final String taskId) throws NotFoundException {

        log.info("Request to patch task by id", kv("API", "PATCH_TASK_ID"));

        deleteTask.process(Long.parseLong(taskId));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    private TaskVO buildResponse(final Task task) {
        return TaskMapper.MAPPER.taskToVO(task);
    }

    private List<TaskVO> buildListResponse(final List<Task> taskList) {
        return TaskMapper.MAPPER.listTaskToVO(taskList);
    }

    private Task buildTask(final TaskVORequest request) {
        return TaskMapper.MAPPER.taskRequestToDomain(request);
    }
}
