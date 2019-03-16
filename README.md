# quarkus-example

An example Quarkus Project suited for more complex examples. 
Using CDI, JPA, Logging, Configuration and Metering.

## Known Issues

Not Java 11 yet, but the folks at GraalVM are working toward it.

Quarkus doesn't respect the test classpath with resources. This leads to resource
loading constraints with application.properties and/or import.sql statements that
should not be in production code bases.

Member variables are package level variable in order to facilitate the restricted CDI
implementation and lack of reflection.

## Setup

### OS X

1. Install Docker
2. Install Maven into /opt/java/maven
3. Install [GraalVM](https://www.graalvm.org/downloads/) into /opt/graalvm
4. Alter .bash_profile or .zshrc to include
    1. `export GRAALVM_HOME=/opt/graalvm`
    2. `export JAVA_HOME=$GRAALVM_HOME`
    3. `export PATH=$JAVA_HOME/bin:$PATH`

### Windows 10 [LATEST]

1. Install Windows Subsystem for Linux
2. Install Maven
3. From WSL
    1. `sudo apt-get install zlib1g-dev`
    2. Install [GraalVM](https://www.graalvm.org/downloads/) into /opt/graalvm
    3. Update your .bashrc or .zshrc file  
        1. `export GRAALVM_HOME=/opt/graalvm`
        2. `export JAVA_HOME=$GRAALVM_HOME`
        3. `export PATH=$JAVA_HOME/bin:$PATH`
4. Install Docker

## Build

1. Start the docker database `docker-compose start db` from the project root.
2. Start the server: `mvn clean compile quarkus:dev`
3. Test: `curl http://localhost:8080/api/users/test_load`

Output (Similar):
```json
[{"cr_dt":"2019-03-15T18:00:07.331Z[UTC]","id":"b27b1a34-bd32-4454-a54e-9ff16f32abce","up_dt":"2019-03-15T18:00:07.331Z[UTC]","ver":0,"name":"test_load","verified":true}]
```

### Standard JAR

```
mvn clean package
```

### Binary Deploy

This requires graalvm be installed and on your path.

```
mvn clean package -Pnative
```
