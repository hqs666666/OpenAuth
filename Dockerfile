FROM openjdk:8-alpine
RUN mkdir -p /app
COPY openauthorization-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
ENV TZ=Asia/Shanghai
ENTRYPOINT ["java","-jar","openauthorization-0.0.1-SNAPSHOT.jar"]

