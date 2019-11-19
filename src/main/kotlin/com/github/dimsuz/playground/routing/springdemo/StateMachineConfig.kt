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
      .listener(listener())
  }

  @Throws(Exception::class)
  override fun configure(states: StateMachineStateConfigurer<Route, Event>) {
    states
      .withStates()
      .initial(Route.FlowLogin)
      .state(Route.FlowLogin)
      .and()
      .withStates()
      .parent(Route.FlowLogin)
      .initial(Route.Login)
      .state(Route.Login)
      .and()

      .withStates()
      .state(Route.FlowOtp)
      .and()
      .withStates()
      .parent(Route.FlowOtp)
      .initial(Route.OtpIntro)
      .states(EnumSet.of(Route.OtpIntro, Route.OtpInput))
      .and()

      .withStates()
      .state(Route.FlowPinCreate)
      .and()
      .withStates()
      .parent(Route.FlowPinCreate)
      .initial(Route.PinIntro)
      .states(EnumSet.of(Route.PinIntro, Route.PinCreate))
      .and()

      .withStates()
      .state(Route.FlowMain)
      .and()
      .withStates()
      .parent(Route.FlowMain)
      .initial(Route.Main)
      .state(Route.Main)
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
        if (transition?.source?.isSubmachineState == true || transition?.target?.isSubmachineState == true) {
          println(
            "Transition started: ${transition.source?.id} => ${transition.target?.id}" +
              ", trigger: ${transition.trigger?.event?.javaClass?.simpleName}"
          )
        } else {
          println(
            "  Transition started: ${transition?.source?.id} => ${transition?.target?.id}" +
              ", trigger: ${transition?.trigger?.event?.javaClass?.simpleName}"
          )
        }
      }

      override fun transitionEnded(transition: Transition<Route, Event>?) {
        if (transition?.source?.isSubmachineState == true || transition?.target?.isSubmachineState == true) {
          println("Transition ended: ${transition.source?.id} => ${transition.target?.id}")
        } else {
          println("  Transition ended: ${transition?.source?.id} => ${transition?.target?.id}")
        }
      }

      override fun stateChanged(from: State<Route, Event>?, to: State<Route, Event>?) {
        if (from?.isSubmachineState == true || to?.isSubmachineState == true) {
          println("State change: ${from?.id} => ${to?.id}")
        } else {
          println("  Sub-State change: ${from?.id} => ${to?.id}")
        }
      }
    }
  }
}

