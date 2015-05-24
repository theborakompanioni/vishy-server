vishy-server
===

mvn clean package
cd vishy-server-web/target
mvn docker:build

docker run -p 8080:8080 -t tbk/vishy-server-web