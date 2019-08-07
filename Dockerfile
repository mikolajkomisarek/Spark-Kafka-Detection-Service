ARG OPENJDK_TAG=8u212
FROM openjdk:${OPENJDK_TAG}

ARG SBT_VERSION=1.2.8

WORKDIR /app
ADD . /app

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt streamConsumerService/avroScalaGenerateSpecific && \
  sbt assembly


ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/target/scala-2.12/app-assembly.jar", "pl.com.itti.Main"]

