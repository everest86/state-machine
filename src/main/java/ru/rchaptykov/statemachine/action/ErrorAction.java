package ru.rchaptykov.statemachine.action;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import ru.rchaptykov.statemachine.event.Event;
import ru.rchaptykov.statemachine.state.Stage;

public class ErrorAction implements Action<Stage, Event> {
    @Override
    public void execute(final StateContext<Stage, Event> context) {
        System.out.println("Ошибка при переходе в статус " + context.getTarget().getId());
    }
}