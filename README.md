# JavaMOP

Improved and integrated source code that was forked off the official [JavaMOP](https://github.com/runtimeverification/javamop) and [RV-Monitor](https://github.com/runtimeverification/rv-monitor) repositories, *which are no longer maintained*.

## Prerequisites

We have only tested JavaMOP on:

1. Java 1.8
2. Maven 3.6.3 and above
3. Maven Surefire 2.14 and above
4. Operating System: Linux or OSX

## Setting up

1. **INSTALLING via Docker** Ensure that you have Docker installed. Then, from the same directory as this README.md file, run:

   a. `cd scripts`

   b. `docker build -t mop:latest - < javamopDockerfile`

   c. `docker run -it mop:latest`

   d. In the Docker container, follow instructions in `$HOME/javamop-agent-bundle/README.txt` to set up a Java agent that attaches JavaMOP to running Java processes.

2. **INSTALLING LOCALLY** From the same directory as this README.md file, run:

   a. `bash scripts/install-javaparser.sh`
   
   b. `bash scripts/integration-test.sh`

   The first script installs a modified version of [JavaParser](https://github.com/javaparser/javaparser.git) that this version of JavaMOP depends on. The second command runs all the tests in JavaMOP, makes a JavaMOP agent, installs the JavaMOP agent, integrates the JavaMOP agent into the [Apache Commons FileUpload](https://github.com/apache/commons-fileupload) open-source project, then monitors the tests in that project against [161 specs](https://github.com/owolabileg/property-db/tree/master/annotated-java-api/java). So, understanding and running `scripts/integration-test.sh` is a good way to get started with using JavaMOP.

   NOTE: We are aware of one parsing-related flaky unit test in JavaMOP. When that test fails, the run of the second script will stop. One work-around is to change `mvn clean package -DskipITs` to `mvn clean package -DskipITs -DskipTests` in `scripts/integration-test.sh`. Another work-around is to comment out all occurrences of `exit 1` in `scripts/integration-test.sh`. We plan to fix these tests soon, but please feel free to contribute a pull request if you have a patch.

## Configuration

Several options allow to collect the traces that JavaMOP generates. The trace collection process can be further configured via the contents of the `-dbConfigFile` option. Currently, the file that is passed into the `-dbConfigFile` allows to configure two parameters:
  
   a. Use `db` to specify what DBMS to use. Currently, we support two values: `h2` (default), and `h2-normalized` (experimental).

   b. Setting `dumpDB=true` will cause JavaMOP to write all collected traces to disk. This option is `false` by default.

Beyond this configuration file, you can see all options that are allowed by running `javamop -help` or `rv-monitor -help`.

## Contributing

We are accepting issues and pull requests. We welcome all who are interested to help fix issues.



