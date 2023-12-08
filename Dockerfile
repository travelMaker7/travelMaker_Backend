FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 환경 변수 설정
ENV SPRING_DATASOURCE_URL=jdbc:mysql://travelmaker.c9yvqchakw20.ap-northeast-2.rds.amazonaws.com:3306/travelMaker
ENV SPRING_DATASOURCE_USERNAME=travelmaker
ENV SPRING_DATASOURCE_PASSWORD=travelmaker123
ENV SPRING_REDIS_HOST=redis
ENV SPRING_REDIS_PORT=6379

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "/app.jar"]