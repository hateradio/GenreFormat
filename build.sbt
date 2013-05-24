// http://stackoverflow.com/questions/5999412/adding-scala-compiler-jar-as-a-runtime-dependency
// http://www.scala-lang.org/node/6750

name := "GenreFormat"

description := "Propery format genre tags."

version := "1.0"

scalaVersion := "2.10.1"

organization := "com.github.hateradio"

mainClass := Some("com.github.hateradio.GenreFormat.Application")

seq(ProguardPlugin.proguardSettings :_*)

proguardOptions += "-dontshrink -dontoptimize -dontobfuscate -dontpreverify -dontnote"

proguardOptions += keepMain("com.github.hateradio.GenreFormat.Application")

// addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.1")
libraryDependencies ++= Seq(
	"org.apache.commons" % "commons-lang3" % "3.1",
	"org.scalatest" %% "scalatest" % "1.9.1" % "test",
	"junit" % "junit" % "4.10" % "test",
  "org.scala-lang" % "scala-swing" % "2.10.1"
)