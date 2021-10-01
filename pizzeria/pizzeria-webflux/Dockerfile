FROM openjdk:11-jre-slim

WORKDIR /usr/src/pizzeria

COPY build/libs/pizzeria-webflux.jar pizzeria-webflux.jar

CMD java $JAVA_OPTS -jar pizzeria-webflux.jar