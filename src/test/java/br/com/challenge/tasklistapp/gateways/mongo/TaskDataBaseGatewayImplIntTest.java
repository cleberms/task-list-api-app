package br.com.challenge.tasklistapp.gateways.mongo;

import br.com.challenge.tasklistapp.configs.SpringMongoConfiguration;
import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import com.mongodb.MongoException;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringMongoConfiguration.class, TaskDataBaseGatewayImpl.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskDataBaseGatewayImplIntTest {

    @Autowired
    private TaskDataBaseGateway gateway;

    @Autowired
    private TaskRepository repository;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldSaveAndFindTasksSuccessfully() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        gateway.save(taskListMock.get(0));
        gateway.save(taskListMock.get(1));

        List<Task> taskList = gateway.findAll();

        assertNotNull(taskList);
        assertEquals(2, taskList.size());

        assertTask(taskListMock.get(0), taskList.get(0));
        assertTask(taskListMock.get(1), taskList.get(1));
    }


    @Test public void shouldFindByStatusSuccessfully() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        gateway.save(taskListMock.get(0));
        gateway.save(taskListMock.get(1));

        List<Task> taskList = gateway.findByStatus("WIP");

        assertNotNull(taskList);
        assertEquals(1, taskList.size());

        assertTask(taskListMock.get(0), taskList.get(0));
    }

    @Test public void shouldFindByIdSuccessfully() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        gateway.save(taskListMock.get(0));
        gateway.save(taskListMock.get(1));

        Task task = gateway.findById(5014L);

        assertNotNull(task);

        assertTask(taskListMock.get(0), task);
    }

    @Test(expected = NotFoundException.class)
    public void shouldFindByIdSuccessfullyWhenStatusNotExists() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        gateway.save(taskListMock.get(0));
        gateway.save(taskListMock.get(1));

        try {

            gateway.findById(5004L);
        } catch (Exception ex) {
            assertEquals("Task with id 5004 not found", ex.getMessage());

            throw ex;
        }
    }


    @Test()
    public void shouldDeleteTaskByIdSuccessfully() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        gateway.save(taskListMock.get(0));
        gateway.save(taskListMock.get(1));

        List<Task> taskList = gateway.findAll();

        assertNotNull(taskList);
        assertEquals(2, taskList.size());

        gateway.deleteTaskById(5014L);

        taskList = gateway.findAll();

        assertNotNull(taskList);
        assertEquals(1, taskList.size());
    }

    private List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task("121",5014L, null,null, "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        taskList.add(task);

        task = new Task("122",5015L, null,null, "Test gateway implementation to Repo",TaskStatus.TO_DO,"Cleber Santaterra", "Cleber Santaterra");

        taskList.add(task);

        return taskList;
    }

    private void assertTask(final Task expected, final Task result) {

        assertEquals(expected.getAssignedName(), result.getAssignedName());
        assertEquals(expected.getCreatedAt(), result.getCreatedAt());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getReporterName(), result.getReporterName());
        assertEquals(expected.getStatus(), result.getStatus());
        assertEquals(expected.getTaskId(), result.getTaskId());
        assertEquals(expected.getUid(), result.getUid());
        assertEquals(expected.getUpdateAt(), result.getUpdateAt());
    }
}