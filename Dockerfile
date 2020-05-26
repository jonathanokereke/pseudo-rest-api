FROM openjdk:8-jdk-alpine
COPY ./target/pseudo-api-1.0-SNAPSHOT.jar pseudo-api-1.0-SNAPSHOT.jar
EXPOSE 8080 8082
CMD ["java","-jar","pseudo-api-1.0-SNAPSHOT.jar"]





#FROM openjdk:8-jdk-alpine
#VOLUME /tmp

#Package Application
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#EXPOSE 8082

#ENTRYPOINT ["java","-cp","app:app/lib/*","de.uniklinik.medic.pseudo.Application"]