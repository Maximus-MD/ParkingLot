## Setting Up PostgreSQL with Docker

To quickly set up and run a PostgreSQL database locally using Docker, follow these steps:

### Prerequisites
Make sure you have Docker installed on your machine. If not, you can download it from [Docker's official website](https://www.docker.com/get-started).

### Pulling PostgreSQL Docker Image
Run the following command to pull the PostgreSQL 16.4 image:

```
sudo docker pull postgres:16.4
```

### Starting PostgreSQL Container
After pulling the image, you can start a PostgreSQL container with the following command:
```
sudo docker run --name parking-lot-16.4 -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=endava123 -e POSTGRES_DB=parkinglot_db -d postgres:16.4
```

### Starting the Docker Container with docker-compose
To start the application and its services (e.g., PostgreSQL) 
defined in your docker-compose.yml file, use the following command:
```
sudo docker compose up -d
```

### Running Flyway Migration Scripts
Flyway is used to manage and apply database migrations. To execute the Flyway scripts, 
run the following Maven command from the root directory of your project (where your pom.xml is located):
```
mvn flyway:migrate
```
This command will look for migration scripts in the default db/migration folder (inside src/main/resources by default) 
and apply them to the configured database. Ensure your database configuration in application.properties 
or application.yml matches the target environment.

### Current project status
[![Quality Gate Status](https://sonar.azure1.mddinternship.com/api/project_badges/measure?project=com.endava.md.internship%3Aparkinglot&metric=alert_status&token=sqb_bff22d632cba9167a8001e1bbeab8cde98d548cf)](https://sonar.azure1.mddinternship.com/dashboard?id=com.endava.md.internship%3Aparkinglot)

[![Reliability Rating](https://sonar.azure1.mddinternship.com/api/project_badges/measure?project=com.endava.md.internship%3Aparkinglot&metric=reliability_rating&token=sqb_bff22d632cba9167a8001e1bbeab8cde98d548cf)](https://sonar.azure1.mddinternship.com/dashboard?id=com.endava.md.internship%3Aparkinglot)
