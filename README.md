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

## Fun Stats

* Deployment Size: 57MB
* Start Up time: 0.1356 sec
* Initial Memory footprint: 12MB
* Max Memory Footprint: 256MB
* Build Time: ~ 3min (WSL); 1.5min (Linux); 2min (Mac)

### Modules:
[agroal, cdi, hibernate-orm, jdbc-postgresql, narayana-jta, resteasy, resteasy-jsonb, security, smallrye-jwt]

## Setup

### Linux

1. Install docker-ce and docker-compose
2. Install Maven into /opt/java/maven
3. Install [GraalVM](https://www.graalvm.org/downloads/) into /opt/graalvm
4. Make sure your developer tools are up-to-date
	1. sudo apt install build-essential
	2. sudo apt install zlib1g-dev
5. Alter your .bashrc or .zshrc
	1. `export GRAALVM_HOME=/opt/graalvm`
    	2. `export JAVA_HOME=$GRAALVM_HOME`
    	3. `export PATH=$JAVA_HOME/bin:$PATH`

### OS X

1. Install Docker
2. Install Maven into /opt/java/maven
3. Install [GraalVM](https://www.graalvm.org/downloads/) into `/Library/Java/JavaVirtualMachines/graalvm`
4. Alter .bash_profile or .zshrc to include
    1. `export GRAALVM_HOME=/Library/Java/JavaVirutalMachines/graalvm`
    2. `export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)` - This only works if you don't have java 1.8 installed.
        1. alternative: `export JAVA_HOME=$GRAALVM_HOME/Contents/Home`
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

1. Start the docker database `docker-compose up --no-start db & docker-compose start db` from the project root.
2. Start the docker open trace instance: `docker run -e COLLECTOR_ZI:PKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:latest`
3. Start the server: `mvn clean compile quarkus:dev`
4. Test: `curl http://localhost:8080/api/users/test_load`

Output (Similar):
```json
[{"cr_dt":"2019-03-15T18:00:07.331Z","up_dt":"2019-03-15T18:00:07.331Z","ver":0,"id":1,"name":"test_load","verified":true}]
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

### Test
```
for i in {1..1000}
do
curl http://localhost:8080/api/users/test_load
done
```
