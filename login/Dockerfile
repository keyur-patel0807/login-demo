FROM adoptopenjdk/openjdk15:alpine-jre
EXPOSE 8089
ADD ./target/login-demo.jar login-demo.jar
ENTRYPOINT ["java","-jar","login-demo.jar"]