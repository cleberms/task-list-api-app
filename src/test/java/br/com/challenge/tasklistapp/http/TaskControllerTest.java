package br.com.challenge.tasklistapp.http;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.usecases.QueryTaskById;
import br.com.challenge.tasklistapp.usecases.QueryTasks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = { TaskController.class})
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueryTasks queryTasks;

    @MockBean
    private QueryTaskById queryTaskById;

    private static final String API_PATH = "/api/tasks";


    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldGetAllTasks() throws Exception {
        final List<Task> taskList = getTasks();

        when(queryTasks.process(null)).thenReturn(taskList);

        mockMvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("[0].taskId", is(Integer.parseInt(taskList.get(0).getTaskId().toString()))))
                .andExpect(jsonPath("[0].createdAt", is(taskList.get(0).getCreatedAt().toString())))
                .andExpect(jsonPath("[0].updateAt", is(taskList.get(0).getUpdateAt().toString())))
                .andExpect(jsonPath("[0].description", is(taskList.get(0).getDescription())))
                .andExpect(jsonPath("[0].status", is(taskList.get(0).getStatus().toString())))
                .andExpect(jsonPath("[0].reporterName", is(taskList.get(0).getReporterName())))
                .andExpect(jsonPath("[0].assignedName", is(taskList.get(0).getAssignedName())))
                .andExpect(jsonPath("[1].taskId", is(Integer.parseInt(taskList.get(1).getTaskId().toString()))))
                .andExpect(jsonPath("[1].createdAt", is(taskList.get(1).getCreatedAt().toString())))
                .andExpect(jsonPath("[1].updateAt").doesNotExist())
                .andExpect(jsonPath("[1].description", is(taskList.get(1).getDescription())))
                .andExpect(jsonPath("[1].status", is(taskList.get(1).getStatus().toString())))
                .andExpect(jsonPath("[1].reporterName", is(taskList.get(1).getReporterName())))
                .andExpect(jsonPath("[1].assignedName", is(taskList.get(1).getAssignedName())));
    }

    @Test public void shouldGetTaskById() throws Exception {
        final List<Task> taskList = getTasks();

        when(queryTaskById.process(5004L)).thenReturn(taskList.get(0));

        mockMvc.perform(get("/api/tasks/{id}", "5004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId", is(Integer.parseInt(taskList.get(0).getTaskId().toString()))))
                .andExpect(jsonPath("$.createdAt", is(taskList.get(0).getCreatedAt().toString())))
                .andExpect(jsonPath("$.updateAt", is(taskList.get(0).getUpdateAt().toString())))
                .andExpect(jsonPath("$.description", is(taskList.get(0).getDescription())))
                .andExpect(jsonPath("$.status", is(taskList.get(0).getStatus().toString())))
                .andExpect(jsonPath("$.reporterName", is(taskList.get(0).getReporterName())))
                .andExpect(jsonPath("$.assignedName", is(taskList.get(0).getAssignedName())));
    }

    private List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task("121",5014L, defaultLocalDateTime(),defaultLocalDateTime(), "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        taskList.add(task);

        task = new Task("122",5015L, defaultLocalDateTime(),null, "Test gateway implementation to Repo",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        taskList.add(task);

        return taskList;
    }



    private static LocalDateTime defaultLocalDateTime() {

        final String DEFAULT_DATE_TIME = "2019-07-01T14:56:59.000Z";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return LocalDateTime.parse(DEFAULT_DATE_TIME, formatter);
    }
}