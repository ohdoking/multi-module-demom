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

## Test

### Test match stream

 1. run stream api
```
curl -X GET http://localhost:8080/match/stream
```
 2. save match data
```
curl --location --request POST 'http://localhost:8080/match' \
--header 'Content-Type: application/json' \
--data-raw '{
    "match_id": 2,
    "name": "Barcelona-Real Madrid",
    "start_date": "2021-12-05T19:00:00",
    "status": "COMPLETED",
    "score": "1-2",
    "events": [
        {
            "minute" : 1,
            "type": "GOAL",
            "team": "Barcelona",
            "player-name":"Lionel Messi"
        },
        {
            "minute" : 45,
            "type": "RED",
            "team": "Real Madrid",
            "player_name":"Sergio Ramos"
        },
        {
            "minute" : 50,
            "type": "RED",
            "team": "Real Madrid",
            "player_name":"Sergio Ramos"
        }
    ]
}'
```
## ETC
redis cli in  docker
```
    docker exec -it [docker-container-id] redis-cli -a password
```

# Reference

- https://github.com/gunayus/springio-19/tree/master/live-score-service
- https://github.com/saravanastar/video-streaming