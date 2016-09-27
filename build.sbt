name := """asynchronous-mongodb-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mongodb" % "mongodb-driver-async" % "3.3.0",
  "com.google.code.gson" % "gson" % "2.7"
)
