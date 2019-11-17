package com.github.dimsuz.playground.routing.springdemo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import org.springframework.statemachine.listener.StateMachineListener
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State
import org.springframework.statemachine.transition.Transition
import java.util.*

@Configuration
@EnableStateMachine
class StateMachineConfig : StateMachineConfigurerAdapter<Route, Event>() {

  @Throws(Exception::class)
  override fun configure(config: StateMachineConfigurationConfigurer<Route, Event>) {
    config
      .withConfiguration()
      .autoStartup(true)
      .listener(listener())
  }

  @Throws(Exception::class)
  override fun configure(states: StateMachineStateConfigurer<Route, Event>) {
    states
      .withStates()
      .initial(Route.Login)
      .states(EnumSet.allOf(Route::class.java))
  }

  @Throws(Exception::class)
  override fun configure(transitions: StateMachineTransitionConfigurer<Route, Event>) {
    transitions
      .withLocal()
      .source(Route.PinIntro).target(Route.PinIntro).event(Event.BackPressed)
      .and()
      .withExternal()
      .source(Route.Login).target(Route.OtpIntro).event(Event.LoginSuccessOtpRequired)
      .and()
      .withExternal()
      .source(Route.Login).target(Route.PinIntro).event(Event.LoginSuccessOtpNotRequired)
      .and()
      .withExternal()
      .source(Route.Login).target(Route.Exit).event(Event.BackPressed)
      .and()

      .withExternal()
      .source(Route.OtpIntro).target(Route.OtpInput).event(Event.OtpIntroContinueRequest)
      .and()
      .withExternal()
      .source(Route.OtpIntro).target(Route.Login).event(Event.BackPressed)
      .and()

      .withExternal()
      .source(Route.OtpInput).target(Route.PinIntro).event(Event.OtpInputSuccessful)
      .and()
      .withExternal()
      .source(Route.OtpInput).target(Route.OtpIntro).event(Event.BackPressed)
      .and()

      .withExternal()
      .source(Route.PinIntro).target(Route.PinCreate).event(Event.PinIntroContinueRequest)
      .and()
      .withExternal()
      .source(Route.PinIntro).target(Route.Login).event(Event.BackPressed)
      .and()

      .withExternal()
      .source(Route.PinCreate).target(Route.Main).event(Event.PinCreateSuccess)
      .and()
      .withExternal()
      .source(Route.PinCreate).target(Route.PinIntro).event(Event.BackPressed)
      .and()

      .withExternal()
      .source(Route.Main).target(Route.Exit).event(Event.BackPressed)
  }

  @Bean
  fun listener(): StateMachineListener<Route, Event> {
    return object : StateMachineListenerAdapter<Route, Event>() {

      override fun stateMachineError(
        stateMachine: StateMachine<Route, Event>?,
        exception: java.lang.Exception?
      ) {
        println("state machine error")
        exception?.printStackTrace()
      }

      override fun eventNotAccepted(event: Message<Event>) {
        System.err.println("Event not accepted ${event.payload}")
      }

      override fun transitionStarted(transition: Transition<Route, Event>?) {
        println("Transition started. Trigger: ${transition?.trigger?.event?.javaClass?.simpleName}")
      }

      override fun stateChanged(from: State<Route, Event>?, to: State<Route, Event>?) {
        println(
          """
          State change:
            from ${from?.id}
            to ${to?.id}
          
          """.trimIndent()
        )
      }
    }
  }
}

