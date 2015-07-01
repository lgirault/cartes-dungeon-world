
enablePlugins(JavaAppPackaging)

name := "CardFactory"

version := "1.0"

sbtVersion := "1.13.8"

scalaVersion := "2.11.7"

libraryDependencies ++=
  Seq(
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
    "org.scala-lang.modules" %% "scala-xml" % "1.0.4"
  )