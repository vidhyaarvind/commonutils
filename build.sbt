name := "commonutils"

version := "1.0"

scalaVersion := "2.10.4"


scalacOptions ++= Seq(
  "-language:postfixOps"
)

libraryDependencies ++= Seq(
  "org.apache.curator" % "curator-framework" % "2.7.1",
  "org.apache.zookeeper" % "zookeeper" % "3.4.6"
)

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")
