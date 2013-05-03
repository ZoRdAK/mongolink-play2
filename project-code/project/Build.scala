import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "mongolink-play2"
  val appVersion      = "0.1"

  val appDependencies = Seq(
    javaCore
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    publishArtifact in(Compile, packageDoc) := false
  )

}
