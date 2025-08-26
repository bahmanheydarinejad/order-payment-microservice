FROM eclipse-temurin:22-jre
ARG JAR=target/*.jar
COPY ${JAR} app.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseG1GC"
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app.jar"]
