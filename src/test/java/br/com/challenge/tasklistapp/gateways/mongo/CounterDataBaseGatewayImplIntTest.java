package br.com.challenge.tasklistapp.gateways.mongo;

import br.com.challenge.tasklistapp.configs.SpringMongoConfiguration;
import br.com.challenge.tasklistapp.domains.Counter;
import br.com.challenge.tasklistapp.gateways.CounterDataBaseGateway;
import br.com.challenge.tasklistapp.gateways.mongo.repository.CounterRepository;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringMongoConfiguration.class, CounterDataBaseGatewayImpl.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CounterDataBaseGatewayImplIntTest {

    @Autowired
    private CounterDataBaseGateway gateway;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    public void shouldToGenerateFisrtTaskNumberWithSuccess() {

        long nextSequence = gateway.getNextTaskSequence();

        assertEquals(1L, nextSequence);
    }

    @Test
    public void shouldToGenerateTaskNumberWithSuccess() {
        final Counter counter = new Counter("1245", "task-sequence", 500);

        gateway.save(counter);

        long nextSequence = gateway.getNextTaskSequence();

        assertEquals(501L, nextSequence);
    }
}
