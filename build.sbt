name := "play-neo4jplugin"

version := "1.4.1-SNAPSHOT"

organization := "com.github.tuxBurner"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Spring releases" at "http://repo.springsource.org/release",
  "Sprin milestones" at "http://repo.spring.io/milestone",
  "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/",
  "Local Maven" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
)

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
   "com.typesafe.play" %% "play" % "2.3.4",
   "com.typesafe.play" %% "play-java" % "2.3.4",
   "com.sun.jersey" % "jersey-core" % "1.18.1",
   // spring data stuff
   "org.springframework" % "spring-context" % "4.1.1.RELEASE",
   "org.springframework.data" % "spring-data-neo4j" % "3.2.0.RELEASE",
   "org.springframework.data" % "spring-data-neo4j-rest" % "3.2.0.RELEASE",
   // neo4j stuff
   "org.neo4j" % "neo4j" % "2.1.4"
)

publishTo <<= version {
  case v if v.trim.endsWith("SNAPSHOT") => Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
  case _ => Some(Resolver.file("Github Pages",  new File("../tuxBurner.github.io/repo")))
}
