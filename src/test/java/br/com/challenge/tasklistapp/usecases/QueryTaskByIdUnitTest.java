package br.com.challenge.tasklistapp.usecases;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.gateways.TaskDataBaseGateway;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class QueryTaskByIdUnitTest {

    @Mock
    private TaskDataBaseGateway gateway;

    @InjectMocks
    private QueryTaskById queryTaskById;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldReturnTaskSuccessfully() throws Exception {

        final Task taskMocked = new Task("121",5014L, defaultLocalDateTime(),defaultLocalDateTime(), "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        when(gateway.findById(any())).thenReturn(taskMocked);

        Task result = queryTaskById.process(Long.valueOf(1));

        assertNotNull(result);

        assertEquals(taskMocked.getAssignedName(), result.getAssignedName());
        assertEquals(taskMocked.getCreatedAt(), result.getCreatedAt());
        assertEquals(taskMocked.getDescription(), result.getDescription());
        assertEquals(taskMocked.getReporterName(), result.getReporterName());
        assertEquals(taskMocked.getStatus(), result.getStatus());
        assertEquals(taskMocked.getTaskId(), result.getTaskId());
        assertEquals(taskMocked.getUid(), result.getUid());
        assertEquals(taskMocked.getUpdateAt(), result.getUpdateAt());

    }

    private static LocalDateTime defaultLocalDateTime() {

        final String DEFAULT_DATE_TIME = "2019-07-01T14:56:59.000Z";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return LocalDateTime.parse(DEFAULT_DATE_TIME, formatter);
    }
}