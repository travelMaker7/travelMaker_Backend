FROM krmp-d2hub-idock.9rum.cc/dev-test/repo_fc189bd1a1c1

# 작업 디렉토리 설정
WORKDIR /app

# 필요한 Python 스크립트를 이미지에 추가
COPY ./build/libs/backend-0.0.1-SNAPSHOT.jar /app/

# 서버가 실행될 때 사용되는 포트
EXPOSE 8080

# 컨테이너를 시작할 때 Python 스크립트를 실행