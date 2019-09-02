FROM java:8
WORKDIR /app
COPY target/task-list-api-app-0.0.1-SNAPSHOT.jar /app/task-list-api-app.jar
ENTRYPOINT ["java","-jar","task-list-api-app.jar"]