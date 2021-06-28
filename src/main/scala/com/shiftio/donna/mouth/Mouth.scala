package com.shiftio.donna.mouth

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.shiftio.donna.global.stuff.{Speak, warnAndIgnore}

import scala.concurrent.ExecutionContextExecutor

object Mouth {
  def apply(): Behavior[Speak] = Behaviors.setup{
    context =>
      implicit val ec: ExecutionContextExecutor = context.executionContext
      context.log.info("Starting up mouth...")

      Behaviors.receiveMessage {
        case Speak(msg) =>
          TTSEngine.getDefault.speak(msg)
          Behaviors.same
        case _ => warnAndIgnore(context)
      }
  }
}
