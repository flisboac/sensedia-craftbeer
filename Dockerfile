FROM debian as builder
RUN apt-get -y update && apt-get install -y openjdk-11-jre-headless maven
ADD ./ /opt/src/app
WORKDIR /opt/src/app
VOLUME /root/.m2
RUN mvn package

FROM openjdk:11-jdk-slim
COPY --from=builder /opt/src/app/target/craft-beer.jar /opt/app/craft-beer.jar
WORKDIR /opt/app
CMD [ "java", "-jar", "/opt/app/craft-beer.jar" ]
