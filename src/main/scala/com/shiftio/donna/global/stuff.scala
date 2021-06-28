package com.shiftio.donna.global

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.{ActorRef, Behavior, Scheduler}
import akka.util.Timeout
import better.files.File
import com.shiftio.donna.Donna
import com.shiftio.donna.global.Constant.Constant

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt

object stuff {
//  val sysActorPath = "akka://embedded-system/user"
  trait Message
  trait MouthMessage
  type Config = Map[String, String]
  type Plugin = (String, File, Config)

  case class RoutedMessage[A](dest: ActorRef[A], message: A) extends Message

  final case class InitiateStartUpSequence() extends Message
  final case class LoadProps() extends Message
  final case class LoadPlugins() extends Message
  final case class PlugsLoaded(plugs: List[Plugin]) extends Message
  final case class PropsLoaded(config: Config) extends Message
  final case class LoadApis() extends Message
  final case class StartUp() extends Message
  final case class StartUpComplete() extends Message
  final case class Quit() extends Message

  final case class Speak(msg: String) extends MouthMessage

  sealed trait Request[A] extends Message
  final case class Response[+A](data: A) extends Message

  final case class ConfigRequest(property: String, _requester: ActorRef[Message]) extends Request[String]
  final case class ConstRequest(const: Constant, _requester: ActorRef[Message]) extends Request[Constant]
  final case class PluginRequest(plugName: String, _requester: ActorRef[Message]) extends Request[Plugin]
  final case class SetConfig(property: String, value: String, _requester: ActorRef[Message]) extends Request[String]

  def warnAndIgnore[T](implicit context: ActorContext[T]): Behavior[T] = {
    context.log.warn("Received unrecognised message, ignoring...")
    Behaviors.ignore
  }

  implicit val scheduler: Scheduler = Donna.sys.scheduler
  implicit val timeout: Timeout = Timeout(5.seconds)
  implicit val ec: ExecutionContextExecutor = Donna.sys.executionContext

}
