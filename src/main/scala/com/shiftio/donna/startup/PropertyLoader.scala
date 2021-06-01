package com.shiftio.donna.startup

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.shiftio.donna.startup.SystemManager.PropsLoaded
import com.shiftio.donna.stuff.Message

object PropertyLoader {

  final case class LoadProps(path: String, ref: ActorRef[Message], system: ActorRef[Message]) extends Message

  def apply(): Behavior[Message] = Behaviors.receive{
    (context, message) =>
      message match {
        case LoadProps(path, ref, system) =>
          // do some property loading
          context.log.info(s"Loading properties from $path")
          Thread.sleep(5000)
          context.log.info("Properties Loaded!")
          context.log.info("Reverting to system manager...")
          ref ! PropsLoaded(system)
          Behaviors.same
        case _ => context.log.info("Unknown command recieved... Ignoring...")
          Behaviors.ignore
      }

  }
}
