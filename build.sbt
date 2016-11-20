name := "common-domain"

organization := "com.hamlazot"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

val akka = "2.4.10"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "com.typesafe.akka" %% "akka-actor" % akka,
  "com.typesafe.akka" %% "akka-slf4j" % akka,
  "org.scalaz" %% "scalaz-core" % "7.2.2",
  "org.atnos" %% "eff-scalaz" % "2.0.0-RC7" exclude("org.scalaz", "scalaz-core"),
  "com.chuusai" %% "shapeless" % "2.3.2",
  "com.typesafe.akka" %% "akka-testkit" % akka % Test,
  "org.specs2" %% "specs2-core" % "3.8.6" % Test,
  "org.specs2" %% "specs2-mock" % "3.8.6" % Test
)

autoCompilerPlugins := true

addCompilerPlugin("com.milessabin" % "si2712fix-plugin_2.11.8" % "1.2.0")
