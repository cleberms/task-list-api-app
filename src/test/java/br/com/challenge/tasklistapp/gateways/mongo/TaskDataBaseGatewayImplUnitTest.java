package br.com.challenge.tasklistapp.gateways.mongo;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import br.com.challenge.tasklistapp.gateways.mongo.repository.TaskRepository;
import com.mongodb.MongoException;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskDataBaseGatewayImplUnitTest {

    @InjectMocks
    private TaskDataBaseGateway gateway = new TaskDataBaseGatewayImpl();

    @Mock
    private TaskRepository repository;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldSaveTaskSuccessfully() {

        final List<Task> taskListMock = getTasks();

        when(repository.save(any(Task.class))).thenReturn(taskListMock.get(0));

        Task taskSaved = gateway.save(new Task());

        assertNotNull(taskSaved);

        assertTask(taskListMock.get(0), taskSaved);

        verify(repository, VerificationModeFactory.times(1)).save(any(Task.class));
    }

    @Test(expected = MongoException.class)
    public void shouldReturnExceptionWhenHappenErrorToSaveTask() {
        when(repository.save(any(Task.class))).thenThrow(new RuntimeException());

        try {
            gateway.save(getTasks().get(0));
        } catch (Exception ex) {
            assertEquals("Error to save task", ex.getMessage());

            verify(repository, VerificationModeFactory.times(1)).save(any(Task.class));

            throw ex;
        }
    }

    @Test public void shouldFindTasksSuccessfully() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        when(repository.findAll()).thenReturn(taskListMock);

        List<Task> taskList = gateway.findAll();

        assertNotNull(taskList);
        assertEquals(2, taskList.size());

        assertTask(taskListMock.get(0), taskList.get(0));
        assertTask(taskListMock.get(1), taskList.get(1));

        verify(repository, VerificationModeFactory.times(1)).findAll();
    }

    @Test(expected = MongoException.class)
    public void shouldReturnExceptionWhenHappenErrorToFindAllTasks() throws NotFoundException {
        doThrow(new RuntimeException()).when(repository).findAll();

        try {
            gateway.findAll();
        } catch (Exception ex) {
            assertEquals("Error to find all tasks", ex.getMessage());

            verify(repository, VerificationModeFactory.times(1)).findAll();

            throw ex;
        }
    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnExceptionWhenNotExistTasksToFindAllTasks() throws NotFoundException {
        when(repository.findAll()).thenReturn(null);

        try {
            gateway.findAll();
        } catch (Exception ex) {
            assertEquals("Tasks not found", ex.getMessage());

            verify(repository, VerificationModeFactory.times(1)).findAll();

            throw ex;
        }
    }

    @Test public void shouldFindByStatusSuccessfully() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        gateway.save(taskListMock.get(0));
        gateway.save(taskListMock.get(1));

        when(repository.findByStatus("WIP")).thenReturn(Arrays.asList(taskListMock.get(0)));

        List<Task> taskList = gateway.findByStatus("WIP");

        assertNotNull(taskList);
        assertEquals(1, taskList.size());

        assertTask(taskListMock.get(0), taskList.get(0));

        verify(repository, VerificationModeFactory.times(1)).findByStatus(anyString());
    }

    @Test(expected = NotFoundException.class)
    public void shouldFindByStatusSuccessfullyWhenStatusNotExists() throws NotFoundException {

        when(repository.findByStatus(any())).thenReturn(null);

        try {

            gateway.findByStatus("DONE");
        } catch (Exception ex) {
            assertEquals("Tasks with status DONE not found", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findByStatus(anyString());

            throw ex;
        }
    }

    @Test(expected = MongoException.class)
    public void shouldReturnExceptionWhenHappenErrorToFindByStatus() throws NotFoundException {

        doThrow(new RuntimeException()).when(repository).findByStatus(anyString());

        try {

            gateway.findByStatus("DONE");
        } catch (Exception ex) {
            assertEquals("Error to find tasks by status DONE", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findByStatus(anyString());

            throw ex;
        }
    }

    @Test public void shouldFindByIdSuccessfully() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        when(repository.findByTaskId(any())).thenReturn(taskListMock.get(0));

        Task task = gateway.findById(5014L);

        assertNotNull(task);

        assertTask(taskListMock.get(0), task);

        verify(repository, VerificationModeFactory.times(1)).findByTaskId(any());
    }

    @Test(expected = NotFoundException.class)
    public void shouldFindByIdSuccessfullyWhenStatusNotExists() throws NotFoundException {

        when(repository.findByTaskId(any())).thenReturn(null);

        try {

            gateway.findById(5004L);
        } catch (Exception ex) {
            assertEquals("Task with id 5004 not found", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findByTaskId(any());

            throw ex;
        }
    }

    @Test(expected = MongoException.class)
    public void shouldReturnExceptionWhenHappenErrorToFindById() throws NotFoundException {

        doThrow(new RuntimeException()).when(repository).findByTaskId(any());

        try {

            gateway.findById(5014L);
        } catch (Exception ex) {
            assertEquals("Error to find task with id = 5014", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findByTaskId(any());

            throw ex;
        }
    }

    @Test()
    public void shouldDeleteTaskByIdSuccessfully() {

        gateway.deleteTaskById(5014L);

        verify(repository, VerificationModeFactory.times(1)).deleteByTaskId(any());
    }

    @Test(expected = MongoException.class)
    public void shouldReturnExceptionWhenHappenErrorToDeleteById() {

        doThrow(new RuntimeException()).when(repository).deleteByTaskId(any());

        try {
            gateway.deleteTaskById(5014L);
        } catch (Exception ex) {
            assertEquals("Error to delete task with id = 5014", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).deleteByTaskId(any());

            throw ex;
        }
    }

    @Test
    public void shouldUpdateTaksSuccessfully() throws NotFoundException {

        final List<Task> taskListMock = getTasks();
        final Task taskToUpdate = taskListMock.get(0);
        taskToUpdate.setStatus(TaskStatus.DONE);

        when(repository.findByTaskId(any())).thenReturn(taskListMock.get(0));
        when(repository.save(any())).thenReturn(taskToUpdate);


        final Task taskUpdated = gateway.update(taskToUpdate);

        assertNotNull(taskUpdated);

        assertEquals(TaskStatus.DONE, taskUpdated.getStatus());

        assertEquals(taskToUpdate.getAssignedName(), taskUpdated.getAssignedName());
        assertEquals(taskToUpdate.getCreatedAt(), taskUpdated.getCreatedAt());
        assertEquals(taskToUpdate.getDescription(), taskUpdated.getDescription());
        assertEquals(taskToUpdate.getReporterName(), taskUpdated.getReporterName());
        assertEquals(taskToUpdate.getTaskId(), taskUpdated.getTaskId());
        assertEquals(taskToUpdate.getUid(), taskUpdated.getUid());
        assertEquals(taskToUpdate.getUpdateAt(), taskUpdated.getUpdateAt());

        verify(repository, VerificationModeFactory.times(1)).findByTaskId(any());
        verify(repository, VerificationModeFactory.times(1)).save(any());
    }

    @Test(expected = MongoException.class)
    public void shouldReturnExceptionWhenHappenErrorToUpdateTaksOnSave() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        when(repository.findByTaskId(any())).thenReturn(taskListMock.get(0));
        doThrow(new RuntimeException()).when(repository).save(any());

        final Task taskToUpdate = taskListMock.get(0);
        taskToUpdate.setStatus(TaskStatus.DONE);

        try {
            gateway.update(taskToUpdate);
        } catch (Exception ex) {
            assertEquals("Error to update task", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findByTaskId(any());
            verify(repository, VerificationModeFactory.times(1)).save(any());

            throw ex;
        }
    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnExceptionWhenHappenErrorToUpdateTaksOnFindTask() throws NotFoundException {

        final List<Task> taskListMock = getTasks();

        doThrow(new RuntimeException()).when(repository).findByTaskId(null);

        final Task taskToUpdate = taskListMock.get(0);
        taskToUpdate.setStatus(TaskStatus.DONE);

        try {
            gateway.update(taskToUpdate);
        } catch (Exception ex) {
            assertEquals("Tasks not found", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findByTaskId(any());

            throw ex;
        }
    }

    private List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task("121",5014L, null,null, "Create Test", "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        taskList.add(task);

        task = new Task("122",5015L, null,null, "Create Test", "Test gateway implementation to Repo",TaskStatus.TO_DO,"Cleber Santaterra", "Cleber Santaterra");

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
        assertEquals(expected.getName(), result.getName());
    }
}