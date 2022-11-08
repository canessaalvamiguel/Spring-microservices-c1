# Microservices using Spring Boot and Spring Cloud Netflix Eureka
Just the code from an [Udemy Course](https://www.udemy.com/course/microservicios-con-spring-boot-y-spring-cloud/)

## Architecture
![Architecture](https://github.com/canessaalvamiguel/Spring-microservices-c1/blob/main/architecture_design.png?raw=true)

## Tech stack:
- [X] Spring Boot
- [X] Spring JPA
- [X] Spring Cloud Eureka
- [X] Spring Cloud Configuration
- [X] Spring Cloud Gateway
- [X] Zuul from Netflix
- [X] Spring Cloud OpenFeign
- [X] Ribbon from Netflix (Load Balancer)
- [X] Hystrix from Netflix (Fault tolerance library)
- [X] Resilience4J (Circuit Breaker)
- [X] JWT
- [X] Spring Security
- [X] Spring Cloud Sleuth
- [X] Spring Cloud Zipkin
- [X] RabbitMQ
- [X] Docker
- [X] MySQL
- [X] PostgreSQL

## Run some API calls
You can import the Postman Json collection I uploaded to this repo. Just import the file to Postman:
- ```Postman_collection.json```

## Run the system using Docker
- Run the system

```bash
 cd config-server
 .\mvnw clean package
 docker build -t config-server:v1 .

 cd eureka-server
 .\mvnw clean package
 docker build -t eureka-server-service:v1 .
 
 cd products-service
 .\mvnw clean package -DskipTests
 docker build -t products-service:v1 .
 
 cd zuul-server
 .\mvnw clean package -DskipTests
 docker build -t zuul-server-service:v1 .
 
 cd user-service
 .\mvnw clean package -DskipTests
 docker build -t user-service:v1 .
 
 cd oauth-service
 .\mvnw clean package -DskipTests
 docker build -t oauth-service:v1 .
 
 cd items-service
 .\mvnw clean package -DskipTests
 docker build -t service-items:v1 .

 cd Spring-microservices-c1
 docker-compose up
```

- Delete containers:
```bash
 cd Spring-microservices-c1
 docker-compose down -v
```

## Run the system using IntelliJ
```
- Checkout to branch 'main-no-docker'
- Run the config-server
- Run the eureka-server
- Run the user-service
- Run the products-service
- Run the oauth-service
- Run the items-service
- Install RabbitMQ and then run it
- Follow the steps availe in zipkin folder then run zipkin (using cmd file for Windows)
- Run the zuul-server or gateway-server
```

## In case you make changes in the code
Please remember you need to create new tags, or delete the existing images using:
```bash
 cd Spring-microservices-c1
 docker-compose down -v
 
 docker rmi config-server:v1
 docker rmi eureka-server-service:v1
 docker rmi products-service:v1
 docker rmi zuul-server-service:v1
 docker rmi user-service:v1
 docker rmi oauth-service:v1
 docker rmi service-items:v1
```
Then follow the steps from : [Run the system using Docker section](#Run-the-system-using-Docker)

## Create images and run containers manually (in case you need)
- create the network:
```
docker network create springcloud-c1
```
- config-server
```
cd config-server
.\mvnw clean package
docker build -t config-server:v1 .
docker run -p 8888:8888 --name config-server --network springcloud-c1 config-server:v1
```

- eureka-server
```
cd eureka-server
.\mvnw clean package
docker build -t eureka-server-service:v1 .
docker run -p 8761:8761 --name eureka-server-service --network springcloud-c1 eureka-server-service:v1
```

- mysql
```
docker pull mysql:8
docker run -p 3306:3306 --name mysql-springcloud-c1 --network springcloud-c1 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=db_spring_cloud -d mysql:8
```

- postgresql
```
docker pull postgres:12-alpine
docker run -p 5432:5432 --name postgres-springcloud-c1 --network springcloud-c1 -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=db_spring_cloud -d postgres:12-alpine
```

- products-service
```
cd products-service
.\mvnw clean package -DskipTests
docker build -t products-service:v1 .
docker run -P --network springcloud-c1 products-service:v1
```

- zuul-server
```
cd zuul-server
.\mvnw clean package -DskipTests
docker build -t zuul-server-service:v1 .
docker run -p 8090:8090 --name zuul-server-service --network springcloud-c1 zuul-server-service:v1
```

- user-service
```
cd user-service
.\mvnw clean package -DskipTests
docker build -t user-service:v1 .
docker run -P --network springcloud-c1 user-service:v1
```

- oauth-service
```
cd oauth-service
.\mvnw clean package -DskipTests
docker build -t oauth-service:v1 .
docker run -p 9100:9100 --network springcloud-c1 oauth-service:v1
```

- items-service
```
cd items-service
.\mvnw clean package -DskipTests
docker build -t service-items:v1 .
docker run -p 8005:8005 -p 8007:8007 --network springcloud-c1 service-items:v1
```

- rabbitmq
```
docker pull rabbitmq:3.10-management
docker run -p 15672:15672 -p 5672:5672 --name springcloud-rabbitmq --network springcloud-c1 -d rabbitmq:3.8-management-alpine
```

- zipkin
```
cd zipkin
docker build -t zipkin-server:v1 .
docker run -p 9411:9411 --name zipkin-server --network springcloud-c1 -e RABBIT_ADDRESSES=springcloud-rabbitmq:5672 -e STORAGE_TYPE=mysql -e MYSQL_USER=zipkin -e MYSQL_PASS=zipkin -e MYSQL_HOST=mysql-springcloud-c1 zipkin-server:v1
```
