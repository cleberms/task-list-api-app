package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.gateways.CounterDataBaseGateway;
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

public class CreateTaskUnitTest {

    @Mock
    private TaskDataBaseGateway taskGateway;

    @Mock
    private CounterDataBaseGateway counterGateway;

    @InjectMocks
    private CreateTask createTask;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldCreateTaskSuccessfully() throws Exception {

        final Task task = new Task("121",null, LocalDateTime.now(),null, "Create Teste", "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        when(taskGateway.save(any())).thenReturn(task);
        when(counterGateway.getNextTaskSequence()).thenReturn(500L);

        Task result = createTask.process(task);

        ArgumentCaptor<Task> argumentCaptorTask = ArgumentCaptor.forClass(Task.class);
        verify(this.taskGateway, VerificationModeFactory.times(1)).save(argumentCaptorTask.capture());
        final Task taskCaptor = argumentCaptorTask.getValue();

        assertNotNull(result);
        assertEquals(result.getUid(), taskCaptor.getUid());
        assertEquals(result.getReporterName(), taskCaptor.getReporterName());
        assertEquals(result.getDescription(), taskCaptor.getDescription());
        assertEquals(result.getName(), taskCaptor.getName());
        assertEquals(result.getUpdateAt(), taskCaptor.getUpdateAt());
        assertTrue(500L == taskCaptor.getTaskId());
        assertEquals(TaskStatus.TO_DO, taskCaptor.getStatus());
        assertEquals(result.getCreatedAt(), taskCaptor.getCreatedAt());

        verify(counterGateway, VerificationModeFactory.times(1)).getNextTaskSequence();
        verify(taskGateway, VerificationModeFactory.times(1)).save(any());
    }
}