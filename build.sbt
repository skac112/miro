// lazy val root = (project in file(".")).
//   settings(
name := "miro"
version := "1.0.5-SNAPSHOT"
scalaVersion := "2.12.4"
exportJars := true
organization := "skac"
libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.8"
libraryDependencies += "skac" %% "vgutils" % "0.1.0-SNAPSHOT"
  // ).
// enablePlugins(ScalaJSPlugin)
