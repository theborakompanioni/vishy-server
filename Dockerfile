FROM java:8

VOLUME /tmp

EXPOSE 8080

ADD vishy-server-web/target/vishy-server-web-1.1.0-SNAPSHOT.jar app.jar

RUN bash -c 'touch /app.jar'

ENTRYPOINT [ "java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=production","-jar","/app.jar"]
