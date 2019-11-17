package com.github.dimsuz.playground.routing.springdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.statemachine.StateMachine

@SpringBootApplication
class SpringdemoApplication : CommandLineRunner {
  @Autowired
  private lateinit var stateMachine: StateMachine<Route, Event>

  override fun run(vararg args: String?) {
    stateMachine.sendEvent(Event.LoginSuccessOtpRequired)
    stateMachine.sendEvent(Event.BackPressed)
    stateMachine.sendEvent(Event.LoginSuccessOtpNotRequired)
    stateMachine.sendEvent(Event.BackPressed)

    stateMachine.sendEvent(Event.LoginSuccessOtpRequired)
    stateMachine.sendEvent(Event.OtpIntroContinueRequest)
    stateMachine.sendEvent(Event.OtpInputSuccessful)
    stateMachine.sendEvent(Event.PinIntroContinueRequest)
    stateMachine.sendEvent(Event.PinCreateSuccess)
    stateMachine.sendEvent(Event.BackPressed)
  }
}

enum class Flow {
  Login,
  Otp,
  PinCreate,
  PinValidate,
  Main
}

enum class Route {
  Login,
  PinIntro,
  PinCreate,
  FingerprintIntro,
  FingerprintCreate,
  PinValidate,
  FingerprintValidate,
  OtpIntro,
  OtpInput,
  Main,
  BooksMain,
  MoviesMain,
  BookDetails,
  BookPurchase,
  MovieDetails,
  MoviePurchase,

  Exit
}

sealed class Event {
  object BackPressed : Event()
  object LoginSuccessOtpRequired : Event()
  object LoginSuccessOtpNotRequired : Event()
  object OtpIntroContinueRequest : Event()
  object OtpInputSuccessful : Event()

  object PinIntroContinueRequest : Event()
  object PinCreateSuccess : Event()
}

// Идеи:
// * У Spring невозможно сказать: source(S1).target(S2).eventIs(Event.Something::class), чтобы потом
//   в guard-е уже разобраться, хотим этот event или нет. Приходится заводить несколько event-ов
//   Или это лучше?
//   UPD: вместо этого они посылают с каждым event-ом payload, его можно достать из StateContext

fun mainSpring(args: Array<String>) {
  runApplication<SpringdemoApplication>(*args)
}
