import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "playframework-neo4j-template"
  val appVersion      = "1.0"

  val appDependencies = Seq(
    javaCore,
    "com.github.tuxBurner" %% "play-neo4jplugin" % "1.0.1"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "tuxburner.github.io" at "http://tuxburner.github.io/repo"
  )

}