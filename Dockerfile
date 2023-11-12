FROM debian:stable-20231030-slim
MAINTAINER wanyu
ADD target/app /opt
EXPOSE 8080
ENTRYPOINT ["/opt/app"]