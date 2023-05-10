package ru.rchaptykov.statemachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import ru.rchaptykov.statemachine.action.ErrorAction;
import ru.rchaptykov.statemachine.action.GetAggregate;
import ru.rchaptykov.statemachine.action.LockBprof;
import ru.rchaptykov.statemachine.action.UnlockBprof;
import ru.rchaptykov.statemachine.event.Event;
import ru.rchaptykov.statemachine.persist.JobPersister;
import ru.rchaptykov.statemachine.state.Stage;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<Stage, Event> {
    @Override
    public void configure(StateMachineConfigurationConfigurer<Stage, Event> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<Stage, Event> states) throws Exception {
        states
                .withStates()
                .initial(Stage.INITIAL)
                .states(EnumSet.allOf(Stage.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<Stage, Event> transitions) throws Exception {
        transitions
                .withExternal()
                .source(Stage.INITIAL)
                .target(Stage.FIRST)
                .event(Event.PROCCED)
                .action(lockBprof(), errorAction())

                .and()
                .withExternal()
                .source(Stage.FIRST)
                .target(Stage.SECOND)
                .event(Event.PROCCED)
                .action(getAggregate(), errorAction())

                .and()
                .withExternal()
                .source(Stage.SECOND)
                .target(Stage.THIRD)
                .event(Event.PROCCED)
                .action(unlockBprof(), errorAction());
    }

    private Action<Stage, Event> lockBprof(){
        return new LockBprof();
    }

    @Bean
    public Action<Stage, Event> errorAction() {
        return new ErrorAction();
    }

    @Bean
    public Action<Stage, Event> getAggregate() {
        return new GetAggregate();
    }

    @Bean
    public Action<Stage, Event> unlockBprof() {
        return new UnlockBprof();
    }

    @Bean
    public StateMachinePersister<Stage, Event, String> persister() {
        return new DefaultStateMachinePersister<>(new JobPersister());
    }
}
