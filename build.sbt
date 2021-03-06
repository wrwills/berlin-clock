lazy val root = (project in file(".")).
  settings(
    name := "clocks",
    version := "1.0",
    scalaVersion := "2.12.2"
  )

scalacOptions := Seq("-unchecked", "-deprecation")

libraryDependencies += "com.github.benhutchison" %% "mouse" % "0.10-MF"

libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "com.beachape" %% "enumeratum" % "1.5.12"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"