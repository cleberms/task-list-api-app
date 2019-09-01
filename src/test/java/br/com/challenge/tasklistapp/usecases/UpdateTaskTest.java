package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UpdateTaskTest {
    @Mock
    private TaskDataBaseGateway gateway;

    @InjectMocks
    private UpdateTask updateTask;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldUpdateTaskSuccessfully() throws Exception {

        final Task task = new Task("121",null, LocalDateTime.now(),null, "Create Teste", "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        when(gateway.update(any())).thenReturn(task);

        Task result = updateTask.process(task, 50178L);

        ArgumentCaptor<Task> argumentCaptorTask = ArgumentCaptor.forClass(Task.class);
        verify(this.gateway, VerificationModeFactory.times(1)).update(argumentCaptorTask.capture());
        final Task taskCaptor = argumentCaptorTask.getValue();

        assertNotNull(result);
        assertEquals(result.getUid(), taskCaptor.getUid());
        assertEquals(result.getReporterName(), taskCaptor.getReporterName());
        assertEquals(result.getDescription(), taskCaptor.getDescription());
        assertEquals(result.getName(), taskCaptor.getName());
        assertEquals(result.getUpdateAt(), taskCaptor.getUpdateAt());
        assertTrue(50178L == taskCaptor.getTaskId());
        assertEquals(TaskStatus.WIP, taskCaptor.getStatus());
        assertEquals(result.getCreatedAt(), taskCaptor.getCreatedAt());
    }

    @Test
    public void shouldUpdatePatchTaskSuccessfully() throws Exception {

        final Task task = new Task("121",50178L, LocalDateTime.now(),LocalDateTime.now(), "Create Teste", "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        when(gateway.findById(any())).thenReturn(task);
        when(gateway.update(any())).thenReturn(task);

        final Task taskToUpdate = new Task();
        task.setStatus(TaskStatus.DONE);
        task.setName("Finalized");

        Task result = updateTask.processPatch(taskToUpdate, 50178L);

        ArgumentCaptor<Task> argumentCaptorTask = ArgumentCaptor.forClass(Task.class);
        verify(this.gateway, VerificationModeFactory.times(1)).update(argumentCaptorTask.capture());
        final Task taskCaptor = argumentCaptorTask.getValue();

        assertNotNull(result);

        assertEquals(TaskStatus.DONE, taskCaptor.getStatus());
        assertEquals("Finalized", taskCaptor.getName());

        assertEquals(result.getUid(), taskCaptor.getUid());
        assertEquals(result.getReporterName(), taskCaptor.getReporterName());
        assertEquals(result.getDescription(), taskCaptor.getDescription());
        assertEquals(result.getUpdateAt(), taskCaptor.getUpdateAt());
        assertTrue(50178L == taskCaptor.getTaskId());
        assertEquals(result.getCreatedAt(), taskCaptor.getCreatedAt());
    }
}