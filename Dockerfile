FROM debian:stable-20231030-slim
COPY target/app /opt
EXPOSE 8080
ENTRYPOINT ["/opt/app"]

