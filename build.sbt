name := "Streamtaxi"

version := "0.1"

scalaVersion := "2.11.10"

lazy val commonSettings = Seq(
  organization := "com.taxiu",
  version := "0.1.0-SNAPSHOT"
)
lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "fat-jar-test"
  ).
  enablePlugins()
mainClass in Compile := Some("BaseMain")
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.4",
  "org.apache.spark" %% "spark-sql" % "2.4.4",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.4"
)