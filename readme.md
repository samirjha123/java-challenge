# Pre-requisite
- use java 8
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
username: admin
password: password

Sample Request: http://localhost:8080/api/v1/employees
```
curl --location --request POST 'http://localhost:8080/api/v1/employees' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=C4A34AD4BBF0DB13BA552C4CEA49B556' \
--data-raw '{
"department": "string",
"name": "string",
"salary": 0
}'
```
### Remaining work for improvement:
1. Spring security improvement
2. Redis key and config (cluster) improvement
3. Mysql master slave configuration
4. Logging and monitoring
### My experience in Java:
- I have 8.5 years of experience in Java (from version 7 to 11 now) and I started to use Spring Boot from last 4 years.
