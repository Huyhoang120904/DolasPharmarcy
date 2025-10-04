FROM maven:3.9.8-amazoncorretto-21 AS build

WORKDIR /app

COPY  . .

RUN mvn package -DskipTests

FROM amazoncorretto:21.0.4

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar backend-0.0.1-SNAPSHOT.jar

# Command to run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080

CMD ["java","-jar", "backend-0.0.1-SNAPSHOT.jar"]

