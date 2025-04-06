## About The Project

A simple Spring based blog.

### Built With

* Spring
* Thymeleaf
* PostgreSQL
* Hibernate
* Minio

## Getting Started

### Prerequisites

* Java 21
* Maven
* Docker
* Tomcat

### Installation

1. Clone the repo
2. Install dependencies
3. Manually configure Tomcat to host an app

### Environment Variables

Environment variables can be found in `src/main/resources/application.properties`.
You may change them as you see fit.
If you want to use docker-compose, there's a script `generate-env.sh` to create an .env file for docker
