FROM krmp-d2hub-idock.9rum.cc/goorm/gradle:8.3.0-jdk17 as build

WORKDIR /app

COPY . .

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties
RUN gradle wrapper
RUN ./gradlew clean build -x test

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "build/libs/backend-0.0.1-SNAPSHOT.jar"]