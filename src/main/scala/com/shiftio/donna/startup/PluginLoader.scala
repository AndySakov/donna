package com.shiftio.donna.startup

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.shiftio.donna.startup.SystemManager.PlugsLoaded
import com.shiftio.donna.stuff.Message

import scala.util.Random

object PluginLoader {
  case class LoadPlugins(path: String, ref: ActorRef[Message], system: ActorRef[Message]) extends Message

  def apply(): Behavior[Message] = Behaviors.receive{
    (context, message) =>
      message match {
        case LoadPlugins(path, ref, system) =>
          context.log.info(s"Loading plugs from $path")
          Thread.sleep(5000)
          context.log.info("Plugs loaded successfully!")
          context.log.info("Reverting back to system manager")
          context.log.info(s"${Random.between(1, 60)} plugins loaded")
          ref ! PlugsLoaded(system)

          Behaviors.same
        case _ =>
          Behaviors.ignore
      }
  }
}
