#install Spring Boot artifact
FROM openjdk:8
VOLUME /tmp
ADD ${project.artifactId}-${project.version}.jar /${project.artifactId}.jar
RUN sh -c 'touch /${project.artifactId}.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/${project.artifactId}.jar"]