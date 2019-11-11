package com.github.dimsuz.playground.routing.springdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.statemachine.StateMachine

@SpringBootApplication
class SpringdemoApplication : CommandLineRunner {
  @Autowired
  private lateinit var stateMachine: StateMachine<States, Events>

  override fun run(vararg args: String?) {
    stateMachine.sendEvent(Events.E1)
    stateMachine.sendEvent(Events.E2);
    stateMachine.sendEvent(Events.E2);
  }
}

enum class States {
  SI, S1, S2
}

enum class Events {
  E1, E2
}

fun mainSpring(args: Array<String>) {
  runApplication<SpringdemoApplication>(*args)
}
