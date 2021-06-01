package com.shiftio.donna.startup

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.shiftio.donna.System.StartUpComplete
import com.shiftio.donna.startup.PluginLoader.LoadPlugins
import com.shiftio.donna.startup.PropertyLoader.LoadProps
import com.shiftio.donna.stuff.Message

object SystemManager {

  final case class SystemManager()

  final case class InitiateStartUpSequence(sysName: String, replyTo: ActorRef[Message]) extends Message
  final case class PlugsLoaded(ref: ActorRef[Message]) extends Message
  final case class PropsLoaded(ref: ActorRef[Message]) extends Message

  def apply(): Behavior[Message] = Behaviors.receive {
    (context, message) =>
      message match {
        case InitiateStartUpSequence(_, replyTo) =>
          context.log.info("Starting up!!!")
          val proper = context.spawn(PropertyLoader(), "prop-loader")
          val plugger = context.spawn(PluginLoader(), "plug-loader")
          proper ! LoadProps("/usr/lib/donna/props/", context.self, system = replyTo)
          plugger ! LoadPlugins("/usr/lib/donna/plugs/", context.self, system = replyTo)
          Behaviors.same
        case PlugsLoaded(_) =>
          Behaviors.same
        case PropsLoaded(ref) =>
          ref ! StartUpComplete()
          Behaviors.same
        case _ =>
          Behaviors.ignore
      }
  }
}
