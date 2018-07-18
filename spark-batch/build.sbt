import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.intersys",
      scalaVersion := "2.11.11",
      version      := "0.0.0"
    )),
    name := "spark-batch",
    resolvers += "Spark Packages Repo" at "https://dl.bintray.com/spark-packages/maven",
    libraryDependencies ++= {
      val configVersion = "1.3.1"
      val sparkVersion  = "2.2.0"
      val cassandraConnectorVersion = "2.3.1"
      Seq(
        "com.typesafe" % "config" % configVersion,
        "org.apache.spark"   %% "spark-sql" % sparkVersion,
        "com.datastax.spark" %% "spark-cassandra-connector" % cassandraConnectorVersion,
        scalaTest % Test
      )
    }
  )
