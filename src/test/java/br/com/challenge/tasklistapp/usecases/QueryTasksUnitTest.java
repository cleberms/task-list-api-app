package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class QueryTasksUnitTest {

    @Mock
    private TaskDataBaseGateway gateway;

    @InjectMocks
    private QueryTasks queryTasks;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTasksSuccessfully() throws Exception {

        final List<Task> taskList = getTasks();

        when(gateway.findAll()).thenReturn(taskList);

        List<Task> result = queryTasks.process(null);

        assertNotNull(result);

        assertEquals(2, result.size());

        assertTask(taskList.get(0), result.get(0));
        assertTask(taskList.get(1), result.get(1));

        verify(gateway, VerificationModeFactory.times(1)).findAll();
        verify(gateway, VerificationModeFactory.times(0)).findByStatus(any());
    }

    @Test
    public void shouldReturnTasksByStatusSuccessfully() throws Exception {

        final List<Task> taskList = getTasks();

        when(gateway.findByStatus(anyString())).thenReturn(taskList);

        List<Task> result = queryTasks.process("WIP");

        assertNotNull(result);

        assertEquals(2, result.size());

        assertTask(taskList.get(0), result.get(0));
        assertTask(taskList.get(1), result.get(1));

        verify(gateway, VerificationModeFactory.times(0)).findAll();
        verify(gateway, VerificationModeFactory.times(1)).findByStatus(any());
    }

    private List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task("121",5014L, null,null, "Create Teste", "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        taskList.add(task);

        task = new Task("122",5015L, null,null, "Create Teste", "Test gateway implementation to Repo",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

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