FROM openjdk:11.0.16-jdk-slim as builder
ADD . /src
WORKDIR /src
RUN ./mvnw package -DskipTests

FROM alpine:3.10.3 as packager
RUN apk --no-cache add openjdk11-jdk openjdk11-jmods
ENV PETCLINIC_JRE="/opt/petclinic-jre"
RUN /usr/lib/jvm/java-11-openjdk/bin/jlink \
    --verbose \
    --add-modules \
        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,jdk.unsupported \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --output "$PETCLINIC_JRE"

FROM alpine:3.10.3
LABEL maintainer="Uliana S"
ENV JAVA_HOME=/opt/petclinic-jre
ENV PATH="$PATH:$JAVA_HOME/bin"
COPY --from=packager "$JAVA_HOME" "$JAVA_HOME"
COPY --from=builder /src/target/4-40-*.jar app.jar
RUN addgroup --system weakuser && adduser --system weakuser && adduser weakuser weakuser && chown -R weakuser:weakuser /app.jar
USER weakuser
ARG maxHeapSize=500
ARG minHeapSize=100
ENV Xmx=$maxHeapSize Xms=$minHeapSize
VOLUME /target/classes
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]