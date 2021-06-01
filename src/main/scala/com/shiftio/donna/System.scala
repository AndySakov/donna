package com.shiftio.donna

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.shiftio.donna.startup.SystemManager
import com.shiftio.donna.startup.SystemManager.InitiateStartUpSequence
import com.shiftio.donna.stuff.Message

object System extends App {

  final case class StartUp() extends Message
  final case class StartUpComplete() extends Message

  def apply(): Behavior[Message] = Behaviors.setup {
    context =>
      val sysMan = context.spawn(SystemManager(), "system-manager")

      Behaviors.receiveMessage {
        case StartUp() =>
          sysMan ! InitiateStartUpSequence("donna", context.self)
          Behaviors.same
        case StartUpComplete() =>
          context.log.info("startup sequence completed!")
          Behaviors.stopped
        case _ =>
          Behaviors.ignore
      }
  }

  ActorSystem(System(), "embedded-system") ! StartUp()

}
