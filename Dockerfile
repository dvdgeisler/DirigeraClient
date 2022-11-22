FROM openjdk:18 AS dirigera-client-build
COPY ./ /opt/dirigera-client
WORKDIR /opt/dirigera-client
RUN ./mvnw package

FROM openjdk:18 AS dirigera-client-hass
COPY --from=dirigera-client-build /opt/dirigera-client/dirigera-client-mqtt/target/*.jar /opt/dirigera-client-hass/dirigera-client-hass.jar
WORKDIR /opt/dirigera-client-hass
ENV DIRIGERA_HOSTNAME=""
ENV DIRIGERA_PORT=8443
ENV DIRIGERA_MQTT_HOSTNAME=host.docker.internal
ENV DIRIGERA_MQTT_PORT=1883

CMD ["java", "-jar", "dirigera-client-hass.jar", \
     "--dirigera.hostname=${DIRIGERA_HOSTNAME}", \
     "--dirigera.port=${DIRIGERA_PORT}", \
     "--dirigera.mqtt.hostname=${DIRIGERA_MQTT_HOSTNAME}", \
     "--dirigera.mqtt.port=${DIRIGERA_MQTT_PORT}"]