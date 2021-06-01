name := """donna"""
organization := "com.shiftio"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.14"

//libraryDependencies += "com.typesafe.akka" %% "akka-streamed" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.lihaoyi" %% "cask" % "0.7.3"
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.7.8"
libraryDependencies += "com.lihaoyi" %% "requests" % "0.6.5"
libraryDependencies += "com.lihaoyi" %% "upickle" % "1.3.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test

mainClass in (Compile, run) := Some("Donna")
mainClass in (Compile, packageBin) := Some("Donna")



