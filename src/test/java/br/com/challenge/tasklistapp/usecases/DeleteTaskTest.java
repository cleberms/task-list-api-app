package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import static org.mockito.Mockito.*;

public class DeleteTaskTest {

    @Mock
    private TaskDataBaseGateway gateway;

    @InjectMocks
    private DeleteTask deleteTask;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shoudlDeleteTaskSucesshully()  {
        deleteTask.process(1L);

        verify(gateway, VerificationModeFactory.times(1)).deleteTaskById(any());
    }
}