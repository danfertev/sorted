import sbt._
import sbt.Keys._

object AppBuild extends Build {
  lazy val app = Project(
    "sorted",
    file("."),
    settings = Defaults.defaultSettings ++ Seq(
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.2",
      libraryDependencies ++= Seq(
        "com.typesafe.akka" %% "akka-actor" % "2.2.1",
        "org.specs2" %% "specs2" % "2.2.3" % "test",
        "junit" % "junit" % "4.11" % "test"
      )
    )
  )
}
