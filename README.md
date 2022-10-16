# Link shortener

## Overview

Application allows to reduce long links and track statistics for your business and projects by monitoring the number of hits from your URL with the click counter

### Build with

* Java 11
* Spring Boot
* Redis
* Docker, docker-compose
* Lombok

## Usage

Create `.env` file with a configuration:
```
SERVER_HOST=<server_host>
SERVER_PORT=<server_port>
REDIS_HOST=<redis_host>
REDIS_PORT=<redis_port>
REDIS_PASS=<redis_password>
```

Run following to create `.properties` file from `.env` for Spring Boot importing:

`$ ln -s .env env.properties`

Then spin up docker containers:

`$ docker-compose up`

See allowed endpoints to `http://<server_host>:<server_port>/swagger-ui.html` 

## License

Distributed under the MIT License. See LICENSE for more information.

