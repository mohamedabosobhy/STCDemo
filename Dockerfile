FROM openjdk:11-jre-slim
COPY target/stc-demo-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","stc-demo-0.0.1-SNAPSHOT.jar"]