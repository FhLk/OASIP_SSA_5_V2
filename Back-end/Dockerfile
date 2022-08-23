FROM maven:3.8.6-jdk-11 as build-stage
WORKDIR /OASIP-SSA5
COPY . .
# RUN mvn -Dmaven.test.skip package
RUN mvn install -Dskiptests 

FROM openjdk:17 as production-stage
COPY --from=build-stage /OASIP-SSA5/target/*.jar app.jar
ENV TZ=Asia/Bangkok
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]