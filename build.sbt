name := "play-neo4jplugin"

version := "1.0.6-SNAPSHOT"

organization := "com.github.tuxBurner"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Spring releases" at "http://repo.springsource.org/release",
  "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/"
)

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
   "play" %% "play" % "2.1.3",
   "play" %% "play-java" % "2.1.3",
    // spring data neo4j
    "javax.inject" % "javax.inject" % "1",
    "asm" % "asm" % "3.3.1",
    "org.springframework" % "spring-context" % "3.2.2.RELEASE",
    "org.springframework.data" % "spring-data-neo4j" % "2.2.2.RELEASE",
    "org.springframework.data" % "spring-data-neo4j-rest" % "2.2.2.RELEASE" excludeAll(
      ExclusionRule(organization = "org.neo4j", name="neo4j")
      ),
    // neo4j
    "org.neo4j" % "neo4j" % "1.9.2",
    "org.neo4j" % "neo4j-rest-graphdb" % "1.9.M04"
)

publishTo <<= version {
  case v if v.trim.endsWith("SNAPSHOT") => Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository"))) //Some(Resolver.file("Github Pages", Path.userHome / "workspace_play" / "tuxBurner.github.io" / "repo-snapshots" asFile))
  case _ => Some(Resolver.file("Github Pages", Path.userHome / "workspace_play" / "tuxBurner.github.io" / "repo" asFile))
}

javacOptions ++= Seq("-source", "1.6")
