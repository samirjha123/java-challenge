# Local deployment using docker-compose:
* Change mongo db url in application.yml
* Close previous running container: -> docker-compose down
* Build jar file: -> mvn clean install
* Build and execute docker image: ->  docker-compose up --build -d

# Deployment using k8s:
## Requirements:
### Set up
* Download and install `Docker Desktop`
* Download and run `Minikube` using command: minikube start --vm-driver=docker --memory='4000mb'

## Deployment Process:
### Build docker image
* docker build --tag api:0.0.1-SNAPSHOT .
### Tag Image
* docker tag api:0.0.1-SNAPSHOT ${docker-id}/api:0.0.1-SNAPSHOT
### Push image to docker hub
* docker push ${docker-id}/api:0.0.1-SNAPSHOT
### Deploy application in DEV profile
* kubectl apply -k k8s-manifest/overlays/dev
### Verify pod and service status
* kubectl get all
### forward 8080 port for local access
* kubectl port-forward svc/api-service 8080:8080

### Application log:
* kubectl exec -it api-cb445759-s527w --container api -- /bin/sh
* cat /var/log/api/Application/Application.log

### How to use this spring-boot project
Swagger UI : http://localhost:8080/swagger-ui.html

# I/Fs specification:
## 1. {{end-point}}/coin/deposit
## Request
``` 
curl --location --request POST 'localhost:8080/coin/deposit' \
--header 'Content-Type: application/json' \
--data-raw '{
    "datetime": "2019-10-05T15:40:01+05:30",
    "amount": 1001
}'
```

## Response
```
{
    "id": "6203b5ab8254cd03224ddf73",
    "amount": 1001.0,
    "datetime": "2019-10-05T15:40:01+05:30"
}
```
## 2. {{end-point}}/coin/get
## Request
``` 
curl --location --request GET 'http://localhost:8080/coin/get?id=62039d835d36102e24892fbe' \
--data-raw ''
```

## Response
```
{
    "id": "62039d835d36102e24892fbe",
    "amount": 19.0,
    "datetime": "2019-10-05T17:40:01+05:30"
}
```
## 3. {{end-point}}/coin/list
## Request
``` 
curl --location --request POST 'localhost:8080/coin/list' \
--header 'Content-Type: application/json' \
--data-raw '{
   "startDatetime": "2019-10-05T07:40:00+04:00",
   "endDatetime": "2019-10-05T09:40:04+01:00"
}'
```

## Response
```
{
    "content": [
        {
            "balance": 1264.0,
            "datetime": "2019-10-05T08:00:00Z"
        },
        {
            "balance": 4414.0,
            "datetime": "2019-10-05T09:00:00Z"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 20,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "first": true,
    "number": 0,
    "numberOfElements": 2,
    "size": 20,
    "empty": false
}
```

### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years
