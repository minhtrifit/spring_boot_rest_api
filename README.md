# 1. Install mysql

## Run Docker: `docker-compose -f .\docker-compose.yml up`

## PhpMyAdmin: `http://localhost:8090`

```php
username: root
password: password
```

## Create database: `spring_boot_db`

# 2. Config setting

## application.properties: `src/main/resources/application.properties`

```php
spring.datasource.url=jdbc:mysql://localhost:3306/spring_boot_db
spring.datasource.username=root
spring.datasource.password=password

spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.diatect=org.hibernate.dialect.MySQL8InnoDBDialect
```

# 3. Run Spring Boot app

## Using: `Visual Studio Code, IntelliJ,...`