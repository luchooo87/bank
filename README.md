# Requerimientos
1. Docker
2. Java 11
3. Sistema operativo Ubuntu 

# Despligue en docker
Ejecutar los siguientes comandos
- ./gradlew clean build
- sudo docker network create db
- sudo docker run --net db --rm  --name postgresdb -e POSTGRES_PASSWORD=mysecretpassword -d postgres
- sudo docker exec -it postgresdb psql -U postgres
- CREATE DATABASE bank;
- alter user postgres with password '12123';
- \q
- sudo docker build -t bank .
- sudo docker run --rm --net db -it -p 8080:8080  bank /bin/bash

# Importar archivo postman
Abrir postman e importar el archivo bank.json de la raiz del proyecto
