FROM eclipse-temurin:17-jdk-alpine
# 컨테이너 내에서 사용할 수 있는 변수 지정 -> JAR 파일의 위치를 환경변수의 형태로 선언
# JAR_FILE 변수 정의
# 프로젝트 빌드할 시, build/libs/*.jar의 형태로 jar file이 생성되고, 그 파일의 위치를 변수로 저장하는것
ARG JAR_FILE=./build/libs/*.jar
# JAR 파일 메인 디렉토리에 복사
# 프로젝트의 Jar파일 위치를 참조해 jar파일을 가져와서 (ARG의 JAR_FILE 변수), 컨테이너의 루트 디렉토리에 app.jar의 이름으로 복사
COPY ${JAR_FILE} app.jar

# 환경 변수 설정
ENV SPRING_DATASOURCE_URL=jdbc:mysql://travelmaker.c9yvqchakw20.ap-northeast-2.rds.amazonaws.com:3306/travelMaker
ENV SPRING_DATASOURCE_USERNAME=travelmaker
ENV SPRING_DATASOURCE_PASSWORD=travelmaker123
ENV SPRING_REDIS_HOST=redis
ENV SPRING_REDIS_PORT=6379
RUN echo "Dockerfile start"

# 시스템 진입점(컨테이너가 시작됐을 떄 실행할 스크립트) 정의
    # Docker파일이 Docker엔진을 통해서 컨테이너로 올라갈 때, Docker 컨테이너의 시스템 진입점이 어디인지를 선언
    # java -jar 명령어를 이용해서, 컨테이너의 루트에 위치한 app.jar을 실행하라.

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "/app.jar"]