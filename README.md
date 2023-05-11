# Read Me First

# Requisitos Docker , JDK 17

- first run docker container postgres:
# # docker run --hostname=a47f8685e4c3 --mac-address=02:42:ac:11:00:02 --env=POSTGRES_USER=postgres --env=POSTGRES_PASSWORD=postgres --env=POSTGRES_DB=managerdb --env=PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/postgresql/15/bin --env=GOSU_VERSION=1.16 --env=LANG=en_US.utf8 --env=PG_MAJOR=15 --env=PG_VERSION=15.2-1.pgdg110+1 --env=PGDATA=/var/lib/postgresql/data --volume=/var/lib/postgresql/data -p 5432:5432 --runtime=runc -d postgres:latest

# para executar os comandos abaixo o banco de dados precisa estar de pé

- executar testes ./mvnw test

- empacotar projeto ./mvnw clean install
