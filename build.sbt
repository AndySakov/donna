name := """donna"""
organization := "com.tellidium"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.1"
//libraryDependencies += "com.github.haifengl" %% "smile-scala" % "2.4.0"
libraryDependencies += "com.lihaoyi" %% "cask" % "0.7.3"
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.7.8"
libraryDependencies += "com.lihaoyi" %% "requests" % "0.6.5"
libraryDependencies += "com.lihaoyi" %% "upickle" % "1.3.8"

mainClass in (Compile, run) := Some("Donna")
mainClass in (Compile, packageBin) := Some("Donna")



