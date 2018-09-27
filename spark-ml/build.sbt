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
      val configVersion = "1.3.1"
      // Problem 1 dependencies
      val sparkVersion  = "2.3.0"
      val jpmmlSparkML  = "1.4.5"
      // Problem 2 dependencies
      val akkaHttpVersion = "10.1.1"
      val akkaVersion = "2.5.12"
      val circeVersion = "0.9.3"
      Seq(
        "com.typesafe" % "config" % configVersion,
        // Spark
        "org.apache.spark" %% "spark-core"  % sparkVersion,
        "org.apache.spark" %% "spark-sql"   % sparkVersion,
        "org.apache.spark" %% "spark-mllib" % sparkVersion,
        // Akka toolkit
        "com.typesafe.akka" %% "akka-actor"     % akkaVersion,
        "com.typesafe.akka" %% "akka-stream"    % akkaVersion,
        "com.typesafe.akka" %% "akka-http"      % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
        // Circe
        "io.circe" %% "circe-core"    % circeVersion,
        "io.circe" %% "circe-generic" % circeVersion,
        "io.circe" %% "circe-parser"  % circeVersion,
        // PMML
        "org.jpmml" % "jpmml-sparkml" % jpmmlSparkML,
        // Logging
        "ch.qos.logback" % "logback-classic" % "1.2.3",
        "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
        // Test
        scalaTest % Test
      )
    }
  )
