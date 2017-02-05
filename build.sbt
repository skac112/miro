// lazy val root = (project in file(".")).
  // settings(
name := "Miro"
version := "1.0"
scalaVersion := "2.11.8"
exportJars := true
libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5"
libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.8"
  // ).
// enablePlugins(ScalaJSPlugin)
