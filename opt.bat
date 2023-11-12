
set name=uid-service
set versionPrefix=V1.0.0

set versionSuffix=-%date:~0,4%-%date:~5,2%-%date:~8,2%-RELEASE
set version=%versionPrefix%%versionSuffix%

docker build -f Dockerfile -t activeclub/%name%:%version% -t activeclub/%name%:latest .

docker push activeclub/%name%:%version%
docker push activeclub/%name%:latest
