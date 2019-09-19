import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val sharedSettings = Seq(
  name := "miro",
  version := "1.0.6-SNAPSHOT",
  organization := "skac112",
  scalaVersion := "2.12.8",
  libraryDependencies += "skac112" %%% "vgutils" % "0.1.0-SNAPSHOT",
  libraryDependencies += "org.typelevel" %%% "cats-core" % "1.2.0",
  libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.7.0"
)

val jvmSettings = Seq(
  exportJars := true,
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.6.3" % "test",
  libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6",
  libraryDependencies +=  "org.scalactic" %% "scalactic" % "3.0.1",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
//    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.8"
)

val jsSettings = Seq(

)

lazy val miro = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(sharedSettings)
  .jsSettings(jsSettings) // defined in sbt-scalajs-crossproject
  .jvmSettings(jvmSettings)

lazy val miroJVM = miro.jvm
lazy val miroJS = miro.js