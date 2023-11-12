docker run --rm -it -v /d/software/maven/mavenDocker/settings.xml:/root/.m2/settings.xml:ro -v /d/software/maven/repository:/root/.m2/repository:rw -v /d/project/openSource/uid-service:/mnt/myproject:rw -w /mnt/myproject  vegardit/graalvm-maven:latest-java21 mvn clean -Pnative native:compile

set name=uid-service
set versionPrefix=V1.0.0

set versionSuffix=-%date:~0,4%-%date:~5,2%-%date:~8,2%-RELEASE
set version=%versionPrefix%%versionSuffix%

docker build -f Dockerfile -t activeclub/%name%:%version% -t activeclub/%name%:latest .

docker push activeclub/%name%:%version%
docker push activeclub/%name%:latest
