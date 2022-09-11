# spring-batch-demo
# TEAM MEMBER "PRABEEN SOTI"
## TO Build Project 
### `mvn clean install`
### or
### `./mvnw clean install`

## Note: use `sudo` if any issue occur on running docker image

## Build Docker Image
### In project directory execute command
### `docker compose build`

## Run Docker container
### In project folder execute command on terminal
### `docker compose up -d`

## Down Docker container
### In project folder execute command on terminal
### `docker compose down`

# Available APIS
#
## LOGIN
### POST METHOD: http://localhost:8080/authenticate
### REQUEST BODY: 
### `{"username":"admin","password":"admin"}`
### RESPONSE BODY:
### `{"token":"jwt_token"}`
#
## Execute Batch Task
### GET METHOD: http://localhost:8080/batch/execute
### Header: KEY: Authorization VALUE: Bearer jwt_token
#
## Check Data count on database
### GET METHOD: http://localhost:8080/data/count
### Header: KEY: Authorization VALUE: Bearer jwt_token