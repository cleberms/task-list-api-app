package br.com.challenge.tasklistapp.gateways.mongo;

import br.com.challenge.tasklistapp.domains.Counter;
import br.com.challenge.tasklistapp.gateways.CounterDataBaseGateway;
import br.com.challenge.tasklistapp.gateways.mongo.repository.CounterRepository;
import com.mongodb.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
@Slf4j
public class CounterDataBaseGatewayImpl implements CounterDataBaseGateway {

    @Autowired
    private CounterRepository repository;

    @Autowired
    private MongoTemplate mongo;

    private static final String TASK_SEQUECE_ID = "task-sequence";

    @Override
    public Counter save(final Counter counter) {
        return repository.save(counter);
    }

    @Override
    public long getNextTaskSequence() {

        try {

            if(shouldCreateFirstSequec(TASK_SEQUECE_ID)) {
                save(new Counter(null, TASK_SEQUECE_ID, 1));

                return 1;
            }

            final long sequenceId = getNextSequence(TASK_SEQUECE_ID);

            log.info("Task sequence id = {} generated successfully", kv("TASK_SEQUECE_ID", sequenceId), kv("FEATURE", "GENERATE_TASK_ID"), kv("STATUS", "SUCCESS"));

            return sequenceId;

        } catch (final Exception e) {
            log.error("Error to generate task id.", kv("FEATURE", "GENERATE_TASK_ID"), kv("STATUS", "ERROR"));

            throw new MongoException("Error to generate task sequence id");
        }
    }

    private boolean shouldCreateFirstSequec(final String id) {
        return mongo.find(query(where("id").is(id)),Counter.class).isEmpty();
    }

    private long getNextSequence(final String sequence) {
        final Counter counter = mongo.findAndModify( //
                query(where("id").is(sequence)), //
                new Update().inc("seq", 1), //
                options().returnNew(true), //
                Counter.class);

        return counter.getSeq();
    }
}
