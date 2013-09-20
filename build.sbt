name := "play-neo4jplugin"

version := "1.1.1-SNAPSHOT"

organization := "com.github.tuxBurner"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Spring releases" at "http://repo.springsource.org/release",
  "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/"
)

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
   "com.typesafe.play" %% "play" % "2.2.0",
   "com.typesafe.play" %% "play-java" % "2.2.0",
    // spring data neo4j
    "javax.inject" % "javax.inject" % "1",
    "asm" % "asm" % "3.3.1",
    "org.springframework" % "spring-context" % "3.2.2.RELEASE",
    "org.springframework.data" % "spring-data-neo4j" % "2.3.1.RELEASE",
    "org.springframework.data" % "spring-data-neo4j-rest" % "2.3.1.RELEASE" excludeAll(
      ExclusionRule(organization = "org.neo4j", name="neo4j")
      ),
    // neo4j
    "org.neo4j" % "neo4j" % "1.9.3",
    "org.neo4j" % "neo4j-rest-graphdb" % "1.9.RC2"
)

publishTo <<= version {
  case v if v.trim.endsWith("SNAPSHOT") => Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
  case _ => Some(Resolver.file("Github Pages",  new File("../tuxBurner.github.io/repo")))
}
