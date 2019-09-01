package br.com.challenge.tasklistapp.gateways;

import br.com.challenge.tasklistapp.domains.Counter;

public interface CounterDataBaseGateway {

    Counter save(Counter counter);

    long getNextTaskSequence();
}
