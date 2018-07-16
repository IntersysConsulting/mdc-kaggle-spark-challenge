import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.intersys",
      scalaVersion := "2.11.11",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "spark-batch",
    libraryDependencies ++= {
      val sparkVersion = "2.2.0"
      Seq(
        "org.apache.spark" %% "spark-sql" % sparkVersion,
        scalaTest % Test
      )
    }
  )
