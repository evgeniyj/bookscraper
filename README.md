# Blinkist book of the day scraper

A java web application that downloads a book of the day from Blinkist and stores it in the internal DB (MySQL or any other per your choice), so you can access it via simple web-interface later.

The application contains several modules:
1. A database
2. A Python RESTful service that is used to bypass Cloudflare's anti-bot verification (based on https://github.com/venomous/cloudscraper)
3. The application itself

All the modules executes in separate docker containers.

# Dependencies

1. Docker v17.05+ and Docker Compose v1.8.0+ (optionally)
2. MySQL 8.0.23+
3. Java 8+
4. Tomcat 9.0.44+

# Installation

1. Build a WAR file from the sources, the file "bookscraper-1.0-SNAPSHOT.war" should be built.
2. Copy "bookscraper_compose" to your host with Docker installed.
3. Copy "bookscraper-1.0-SNAPSHOT.war" to "bookscraper_compose/tomcat"
4. cd to "bookscraper_compose" and run "docker-compose up -d --build"
5. Run "docker exec -it bookscraper_mysql bash" and execute ' mysql -h 127.0.0.1 -u root -p"12345" '
   This fixes a bug that prevents the application from connecting to the DB (Access to DialectResolutionInfo cannot be null when 'hibernate.dialect' not set" exception)  
6. Open http://your_host:8080/bookscraper-1.0-SNAPSHOT/ (Usually, the application within 2 minutes)

Alternatively, all the containers can be built based on their dockerfiles and run manually.

# Defaults

1. Folders on the Docker host to persist the data:

MySQL: /home/docker/mysql

Mp3 files and covers: /home/docker/bookstorage

2. Root password for MySQL: 12345

This can be changed in docker-compose.yml and in dockerfiles for Tomcat and MySQL.











