package com.shiftio.donna.mouth

import java.util.Locale
import javax.sound.sampled.AudioInputStream

import marytts.LocalMaryInterface
import marytts.util.data.audio.AudioPlayer

import scala.concurrent.ExecutionContext

trait TTSEngine {

  val voiceName: String
  val locale: Locale

  private lazy val maryTTS = {
    val m = new LocalMaryInterface()
    m.setLocale(locale)
    m.setVoice(voiceName)
    m
  }

  def getAudioStream(inputText: String): AudioInputStream =
    maryTTS.generateAudio(inputText)

  def speak(inputText: String)(implicit ec: ExecutionContext): Unit = {
    val audioStream = getAudioStream(inputText)
    ec.execute(new AudioPlayer(audioStream))
  }

}

object TTSEngine {
  class GenericText2Speech(val voiceName: String, val locale: Locale) extends TTSEngine

  object Poppy extends GenericText2Speech("dfki-poppy-hsmm", Locale.UK) {
    override def speak(inputText: String)
                      (implicit ec: ExecutionContext): Unit = {

      super.speak(
        inputText
          .replaceAll(" – ", " ")
          .replaceAll(" - ", " ")
          .replaceAll("&apos;", "")
          .replaceAll("EU", "E.U.")
          .replaceAll("iot", "I.O.T.").replaceAll("IOT", "I.O.T.").replaceAll("IoT", "I.O.T.")
          .replaceAll("UK", "U.K.").replaceAll(".uk", ".U K.")
          .replaceAll("AI", "A.I.")
          .replaceAll("'", "")
          .replaceAll("’", "")
          .replaceAll("£(\\d+)m", "$1 million pounds")
          .replaceAll("£(\\d+)k", "$1 thousand pounds")
          .replaceAll("\\$(\\d+) (\\w+)", "$1 $2 dollars")
      )
    }
  }

  object Spike extends GenericText2Speech("dfki-spike-hsmm", Locale.UK) {
    override def speak(inputText: String)
                      (implicit ec: ExecutionContext): Unit = {

      super.speak(
        inputText
          .replaceAll(" – ", " ")
          .replaceAll(" - ", " ")
          .replaceAll("&apos;", "")
          .replaceAll("EU", "E.U.")
          .replaceAll("iot", "I.O.T.").replaceAll("IOT", "I.O.T.").replaceAll("IoT", "I.O.T.")
          .replaceAll("UK", "U.K.").replaceAll(".uk", ".U K.")
          .replaceAll("AI", "A.I.")
          .replaceAll("'", "")
          .replaceAll("’", "")
          .replaceAll("£(\\d+)m", "$1 million pounds")
          .replaceAll("£(\\d+)k", "$1 thousand pounds")
          .replaceAll("\\$(\\d+) (\\w+)", "$1 $2 dollars")
      )
    }
  }

  def getDefault: TTSEngine = Poppy
}