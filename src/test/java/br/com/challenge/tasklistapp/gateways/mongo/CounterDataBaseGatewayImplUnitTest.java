package br.com.challenge.tasklistapp.gateways.mongo;

import br.com.challenge.tasklistapp.domains.Counter;
import br.com.challenge.tasklistapp.gateways.mongo.repository.CounterRepository;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CounterDataBaseGatewayImplUnitTest {
    @Mock
    private CounterRepository repository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private CounterDataBaseGatewayImpl gateway;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldSaveSuccessfully() {
        final Counter counter = new Counter("1245", "task-sequence", 500);

        when(repository.save(any())).thenReturn(counter);

        Counter result = gateway.save(counter);

        assertEquals(counter, result);

        verify(this.repository, VerificationModeFactory.times(1)).save(counter);
    }

    @Test(expected = MongoException.class)
    public void shouldToGenerateExceptionWhenDatabaseIsDown() {

        doThrow(new MongoTimeoutException("timeout")).when(mongoTemplate).find(any(Query.class), any(Class.class));

        try {
            final Counter counter = new Counter("1245", "task-sequence", 500);

            gateway.getNextTaskSequence();

        } catch (final Exception e) {
            assertEquals("Error to generate task sequence id", e.getMessage());

            throw e;
        }

    }
}