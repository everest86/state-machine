package ru.rchaptykov.statemachine.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import ru.rchaptykov.statemachine.event.Event;
import ru.rchaptykov.statemachine.state.Stage;

public class UnlockBprof implements Action<Stage, Event> {
    @Override
    public void execute(final StateContext<Stage, Event> context) {
        System.out.println("Профиль разблокирован");
    }
}