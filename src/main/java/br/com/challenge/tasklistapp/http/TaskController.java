package br.com.challenge.tasklistapp.http;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.http.json.TaskVO;
import br.com.challenge.tasklistapp.http.json.mapper.TaskMappers.TaskMapper;
import br.com.challenge.tasklistapp.usecases.QueryTaskById;
import br.com.challenge.tasklistapp.usecases.QueryTasks;
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


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get tasks", response = TaskVO.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskVO>> getAllTaks(
            @ApiParam(allowableValues = "TO_DO, WIP, DONE", allowMultiple = true, allowEmptyValue = true)
            @RequestParam(value = "status", required = false) String status) throws NotFoundException {

        log.info("Request to get all tasks", kv("API", "GET_ALL_TASKS"));

        List<Task> taskList = queryTasks.process(status);

        return new ResponseEntity<>(buildListResponse(taskList), HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Resource to get tasks", response = TaskVO.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskVO> getTak(
            @ApiParam(name = "ID")
            @PathVariable("id") final Long taskId ) throws NotFoundException {

        log.info("Request to get all tasks", kv("API", "GET_TASK_ID"));

        Task task = queryTaskById.process(taskId);

        return new ResponseEntity<>(buildResponse(task), HttpStatus.OK);

    }

    private TaskVO buildResponse(final Task task) {
        return TaskMapper.MAPPER.taskToVO(task);
    }

    private List<TaskVO> buildListResponse(final List<Task> taskList) {
        return TaskMapper.MAPPER.listTaskToVO(taskList);
    }
}
