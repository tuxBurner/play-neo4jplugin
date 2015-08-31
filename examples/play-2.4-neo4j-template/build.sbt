name := """Play-2.4-neo4j-template"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.github.tuxBurner" %% "play-neo4jplugin" % "1.5.0-SNAPSHOT",
  "org.webjars" % "bootstrap" % "3.2.0"

)


resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "tuxburner.github.io" at "http://tuxburner.github.io/repo"

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

resolvers += "Neo4j Maven Repo" at "http://m2.neo4j.org/content/repositories/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
