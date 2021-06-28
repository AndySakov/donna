package com.shiftio.donna

import akka.actor.typed.ActorSystem
import com.colofabrix.scala.figlet4s.unsafe.{FIGureOps, Figlet4s, OptionsBuilderOps}
import com.shiftio.donna.api.{Api, System}
import com.shiftio.donna.global.stuff.StartUp

import scala.util.Random

object Donna extends App {

  /*
    TODO: Add first run config setup and ask to enable color console output
   */

  val asciiStyle = fansi.Bold.On ++ Random.shuffle(fansi.Color.all).head
  val captionStyle = fansi.Underlined.On ++ fansi.Bold.On ++ Random.shuffle(fansi.Color.all).head

  Api.prelims()

  println(
    asciiStyle(Figlet4s
      .builder("Donna   1 . 0 !")
      .render
      .asSeq
      .mkString("\n"))
      ++ captionStyle("\nThe virtual assistant of your dreams!\n")
  )

  val sys = ActorSystem(System(), "embedded-system")
  sys ! StartUp()

}
