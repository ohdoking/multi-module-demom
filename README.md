# multi-module-demom

## run the project

0. run necessary component
```
    docker run -p 6379:6379 --name test-redis redis 
```
1. gradle build
```
    ./gradlew clean build
```
2. build docker image
```
    docker build -f Dockerfile -t multi-demo . 
```
3. run docker
```
    docker run -d --name multi-demo -p 8080:8080 multi-demo
```
