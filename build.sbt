ThisBuild / version := "1.0"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "levenshtein-automaton",
    maintainer := "igorkurilenko@icloud.com",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test,
    libraryDependencies += "junit" % "junit" % "4.13.2" % Test
  )
