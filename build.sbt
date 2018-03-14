name := "play-neo4jplugin"

version := "2.6.0-SNAPSHOT"

organization := "com.github.tuxBurner"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Spring releases" at "http://repo.springsource.org/release",
  "Sprin milestones" at "http://repo.spring.io/milestone",
  "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/",
  "Local Maven" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.4.2",
  "com.typesafe.play" %% "play-java" % "2.4.2",
  "com.sun.jersey" % "jersey-core" % "1.19",
  // spring data stuff
  "org.springframework" % "spring-context" % "4.1.6.RELEASE",
  "org.springframework.data" % "spring-data-neo4j" % "3.4.0.RC1",
  "org.springframework.data" % "spring-data-neo4j-rest" % "3.4.0.RC1",
  // neo4j stuff
  "org.neo4j" % "neo4j" % "2.3.0-M02",
  // Inject stuff
  "javax.inject" % "javax.inject" % "1"
)

publishTo <<= version {
  case v if v.trim.endsWith("SNAPSHOT") => Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
  case _ => Some(Resolver.file("Github Pages",  new File("../tuxBurner.github.io/repo")))
}
