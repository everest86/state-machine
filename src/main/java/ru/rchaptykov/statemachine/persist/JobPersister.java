package ru.rchaptykov.statemachine.persist;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Service;
import ru.rchaptykov.statemachine.event.Event;
import ru.rchaptykov.statemachine.state.Stage;

import java.util.HashMap;

@Service
public class JobPersister implements StateMachinePersist<Stage, Event, String> {

    private final HashMap<String, StateMachineContext<Stage, Event>> contexts = new HashMap<>();

    @Override
    public void write(StateMachineContext<Stage, Event> context, String contextObj) throws Exception {
        contexts.put(contextObj, context);
    }

    @Override
    public StateMachineContext<Stage, Event> read(String contextObj) throws Exception {
        return contexts.get(contextObj);
    }
}
