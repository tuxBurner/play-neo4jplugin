name := "play-neo4jplugin"

version := "1.3.1"

organization := "com.github.tuxBurner"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Spring releases" at "http://repo.springsource.org/release",
  "Sprin milestones" at "http://repo.spring.io/milestone",
  "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/"
)

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
   "com.typesafe.play" %% "play" % "2.2.1",
   "com.typesafe.play" %% "play-java" % "2.2.1",
    // spring data neo4j dependencies
    "javax.inject" % "javax.inject" % "1",
    "asm" % "asm" % "3.3.1",
    "com.sun.jersey" % "jersey-core" % "1.9",
    // spring data stuff
    "org.springframework" % "spring-context" % "3.2.5.RELEASE",
    "org.springframework.data" % "spring-data-neo4j" % "3.0.0.M1",
    "org.springframework.data" % "spring-data-neo4j-rest" % "3.0.0.M1",
    // neo4j stuff
    "org.neo4j" % "neo4j" % "2.0.0-M06"
)

publishTo <<= version {
  case v if v.trim.endsWith("SNAPSHOT") => Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
  case _ => Some(Resolver.file("Github Pages",  new File("../tuxBurner.github.io/repo")))
}
