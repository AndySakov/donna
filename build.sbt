name := """donna"""
organization := "com.shiftio"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.14"
lazy val circeVersion = "0.12.3"

//libraryDependencies += "com.typesafe.akka" %% "akka-streamed" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.1"
//libraryDependencies += "com.github.pathikrit"  %% "better-files-akka"  % _version
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.14"
//libraryDependencies += "com.lihaoyi" %% "cask" % "0.7.3"
libraryDependencies += "com.colofabrix.scala" %% "figlet4s-core" % "0.3.0"
//libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.7.8"
libraryDependencies += "com.lihaoyi" %% "requests" % "0.6.5"
//libraryDependencies += "com.lihaoyi" %% "upickle" % "1.3.8"
libraryDependencies += "com.lihaoyi" %% "fansi" % "0.2.14"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test
libraryDependencies += "dev.zio" %% "zio" % "1.0.8"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.28.0"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

resolvers +=
  "Bintray MaryTTS" at "https://jcenter.bintray.com/"

libraryDependencies += "de.dfki.mary" % "voice-dfki-poppy-hsmm" % "5.2"
libraryDependencies += "de.dfki.mary" % "voice-dfki-spike-hsmm" % "5.2"

libraryDependencies += "org.scalafx" %% "scalafx" % "15.0.1-R21"

lazy val javaFXModules = {
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux")   => "linux"
    case n if n.startsWith("Mac")     => "mac"
    case n if n.startsWith("Windows") => "win"
    case _                            =>
      throw new Exception("Unknown platform!")
  }
  // Create dependencies for JavaFX modules
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map( m=> "org.openjfx" % s"javafx-$m" % "15.0.1" classifier osName)
}

libraryDependencies ++= javaFXModules

mainClass in (Compile, run) := Some("com.shiftio.donna.Donna")
mainClass in (Compile, packageBin) := Some("com.shiftio.donna.Donna")



