package com.shiftio.donna.api

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.shiftio.donna.api.Api._
import com.shiftio.donna.global.Constant
import com.shiftio.donna.global.stuff._
import com.shiftio.donna.mouth.Mouth

import scala.util.Random

object System {

  def systemManager(system: ActorRef[Message]): Behavior[Message] = Behaviors.setup {
    context =>
      implicit val self: ActorRef[Message] = context.self
      val proper = context.spawn(propertyLoader, "prop-loader")
      val plugger = context.spawn(pluginLoader, "plug-loader")
      Behaviors.receiveMessage {
        case InitiateStartUpSequence() =>
          context.log.info("Starting up!!!")
          proper ! LoadProps()
          plugger ! LoadPlugins()
          Behaviors.same
        case PropsLoaded(config) =>
          context.spawn(propertyManager(config), "prop-manager")
          Behaviors.same
        case PlugsLoaded(plugs) =>
          context.spawn(pluginManager(plugs), "plug-manager")
          system ! StartUpComplete()
          Behaviors.same
        case _ =>
          warnAndIgnore(context)
      }

  }

  def apply(): Behavior[Message] = Behaviors.setup {
    context =>
      val sysMan = context.spawn(systemManager(context.self), "system-manager")
      val mouth = context.spawn(Mouth(), "mouthy")

      Behaviors.receiveMessage {
        case StartUp() =>
          mouth ! Speak("Hello Temi!")
          sysMan ! InitiateStartUpSequence()
          Behaviors.same
        case StartUpComplete() =>
          context.log.info("startup sequence completed!")
          context.self ! Quit()
          Behaviors.same
        case Quit() =>
          mouth ! Speak(
            Random.shuffle(
              List("Good Night!", "See you later, alligator", "Buenas Noches, Amigo!", "Guten Nacht, Manner", "After a while, crocodile!", "Privyet, Comrade!")
            ).head
          )
          Constance.getConstant(Constant.LOCK_FILE).map(_.delete())
          Behaviors.stopped
        case _ =>
          warnAndIgnore(context)
      }
  }

}
