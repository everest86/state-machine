package ru.rchaptykov.statemachine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import ru.rchaptykov.statemachine.event.Event;
import ru.rchaptykov.statemachine.state.Stage;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private StateMachinePersister<Stage, Event, String> persister;
    @Autowired
    private StateMachineFactory<Stage, Event> stateMachineFactory;

    @Override
    public String lockBprof(String jobId) {
        final StateMachine<Stage, Event> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.sendEvent(Event.PROCCED);
        try {
            persister.persist(stateMachine, jobId);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return stateMachine.getState().getId().name();
    }

    @Override
    public String getAggregates(String jobId) {
        final StateMachine<Stage, Event> stateMachine = stateMachineFactory.getStateMachine();

        try {
            persister.restore(stateMachine, jobId);
            stateMachine.sendEvent(Event.PROCCED);
            persister.persist(stateMachine, jobId);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return stateMachine.getState().getId().name();
    }

    @Override
    public String unlockBprof(String jobId) {
        final StateMachine<Stage, Event> stateMachine = stateMachineFactory.getStateMachine();

        try {
            persister.restore(stateMachine, jobId);
            stateMachine.sendEvent(Event.PROCCED);
            persister.persist(stateMachine, jobId);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return stateMachine.getState().getId().name();
    }
}
