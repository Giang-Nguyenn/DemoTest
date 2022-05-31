export VERSION=$(cat ./VERSION.txt)
VERSION=$(cat ./VERSION.txt)
echo "VERSION" : $VERSION
echo $VERSION
echo "maven..."
mvn clean package -DskipTests
echo "build images"
#sudo docker build -t springboot:$VERSION .
docker login registry.gitlab.com
sudo docker build -t registry.gitlab.com/g4435/aaa .
sudo docker push registry.gitlab.com/g4435/aaa
#sudo docker-compose up -d
