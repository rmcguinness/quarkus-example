# quarkus-example

An example Quarkus Project

## Setup

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

## Build

### Standard JAR

```
mvn clean package
```

### Binary Deploy

This requires graalvm be installed and on your path.

```
mvn clean package -Pnative
```
