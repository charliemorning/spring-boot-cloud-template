FROM openjdk:8-jdk-alpine
MAINTAINER charlie <zhangchen143@gmail.com>
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/template/app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/template/app.jar"]
