git pull https://salat-23:"${GITHUB_TOKEN}"@github.com/salat-23/wafflesproject.git

mvn clean
mvn package

cp target/wafflesproject-0.0.1-SNAPSHOT.jar src/main/docker

cd src/main/docker

docker-compose build
docker-compose pull
docker-compose up -d
