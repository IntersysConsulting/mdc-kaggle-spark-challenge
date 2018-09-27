import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.intersys",
      scalaVersion := "2.11.8",
      version      := "0.0.0"
    )),
    name := "spark-batch",
    resolvers += "Spark Packages Repo" at "https://dl.bintray.com/spark-packages/maven",
    libraryDependencies ++= {
      val sparkVersion  = "2.3.0"
      Seq(
        "com.typesafe" % "config" % "1.3.1",
        // Spark
        "org.apache.spark" %% "spark-core"  % sparkVersion,
        "org.apache.spark" %% "spark-sql"   % sparkVersion,
        // Logging
        "ch.qos.logback" % "logback-classic" % "1.2.3",
        "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
        // Test
        scalaTest % Test
      )
    }
  )
