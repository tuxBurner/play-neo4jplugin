name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.10.4"

resolvers += "tuxburner.github.io" at "http://tuxburner.github.io/repo"

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"


libraryDependencies ++= Seq(
  cache,
  ws,
  "com.github.tuxBurner" %% "play-neo4jplugin" % "1.4.0-SNAPSHOT",
  "org.webjars" % "bootstrap" % "3.2.0"
)
