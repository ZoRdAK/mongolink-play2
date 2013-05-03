import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "todo"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    //"mongolink-play2" %% "mongolink-play2" % "0.1",
    "org.mongolink" % "mongolink" % "0.0.16-SNAPSHOT"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    resolvers += "MongoLink Repository" at "http://repository-mongolink.forge.cloudbees.com/release/",
    resolvers += "Maven Local Repository" at "file:///C:/Users/Karl/.m2/repository/"
  )

}
