name := """Play-2.4-neo4j-template"""

version := "1.0-SNAPSHOT"

lazy val plugin = RootProject(file("../.."))
lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(plugin % "compile->compile;test->test")

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.github.tuxBurner" %% "play-neo4jplugin" % "1.5.1-SNAPSHOT",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.springframework.data" % "spring-data-neo4j" % "4.1.0.BUILD-SNAPSHOT",
  "org.neo4j" % "neo4j-ogm-core" % "2.0.0-SNAPSHOT"
)


resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Spring releases" at "http://repo.springsource.org/release",
  "Spring milestones" at "http://repo.spring.io/milestone",
  "spring snapshots" at "http://repo.spring.io/snapshot",
  "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/",
  "neo snapshots" at "http://m2.neo4j.org/content/repositories/snapshots"

  // This line below can cause "uri has authority component" errors.
  //"Local Maven" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
