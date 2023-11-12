docker run --rm -it -v /d/software/maven/mavenDocker/settings.xml:/root/.m2/settings.xml:ro -v /d/software/maven/repository:/root/.m2/repository:rw -v /d/project/openSource/uid-service:/mnt/myproject:rw -w /mnt/myproject  vegardit/graalvm-maven:latest-java21 mvn clean -Pnative native:compile

set name=uid-service
set versionPrefix=v1.0.0

set timeStr=%date:~0,4%-%date:~5,2%-%date:~8,2%

set tagNative=activeclub/%name%:%versionPrefix%-native-%timeStr%
set tagJvm=activeclub/%name%:%versionPrefix%-jvm-%timeStr%
set tagLatest=activeclub/%name%:latest

docker build -f build/Dockerfile-native -t %tagNative% .
docker build -f build/Dockerfile-jvm -t %tagJvm% -t %tagLatest% .

docker push %tagNative%
docker push %tagJvm%
docker push %tagLatest%