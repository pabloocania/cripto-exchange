FROM gradle:6.0.1-jdk8 AS build

ENV GRADLE_USER_HOME=/build

WORKDIR /build

COPY gradle.properties .
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY apps apps
COPY components components

USER root
RUN chown -R gradle /build
USER gradle

RUN gradle :exchange-api:installShadow --no-daemon

FROM openjdk:8-alpine

WORKDIR /app

COPY --from=build /build/apps/exchange-api/build/libs/exchanger-api.jar .

CMD ["java", "-jar", "exchange-api.jar"]
