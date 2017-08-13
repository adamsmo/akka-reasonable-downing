import com.typesafe.sbt.SbtMultiJvm.multiJvmSettings
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

HeaderPlugin.autoImport.headerSettings(MultiJvm)
AutomateHeaderPlugin.autoImport.automateHeaderSettings(MultiJvm)

lazy val `akka-reasonable-downing` =
  project
    .in(file("."))
    .settings(multiJvmSettings: _*)
    .enablePlugins(AutomateHeaderPlugin, GitVersioning, GitBranchPrompt)
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
        library.akkaCluster,
        library.akkaMultiNode % Test,
        library.scalaTest     % Test
      )
    )
    .configs (MultiJvm)

lazy val library =
  new {
    object Version {
      val akka      = "2.5.4"
      val scalaTest = "3.0.3"
    }
    val akkaCluster   = "com.typesafe.akka" %% "akka-cluster"            % Version.akka
    val akkaMultiNode = "com.typesafe.akka" %% "akka-multi-node-testkit" % Version.akka
    val scalaTest     = "org.scalatest"     %% "scalatest"               % Version.scalaTest
  }

lazy val settings =
  commonSettings ++
  gitSettings ++
  scalafmtSettings

lazy val commonSettings =
  Seq(
    organization := "pl.immutables",
    organizationName := "Mateusz Bilski",
    startYear := Some(2017),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    scalaVersion := "2.12.3",
    crossScalaVersions := Seq("2.11.11", "2.12.3"),
    releaseCrossBuild := true,
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    parallelExecution in Test := false,
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value)
)

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtOnCompile.in(Sbt) := false,
    scalafmtVersion := "1.1.0"
  )
