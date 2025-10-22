package com.vehiforge.configurationService.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class KafkaMessageProducer(
    private val objectMapper: ObjectMapper,
    // Kafka Template
    private val kafkaTemplate: KafkaTemplate<String, String>,
    // Kafka Topics
    @Value("\${spring.kafka.consumers.config-change.name}")
    private val vehicleConfigChange: String,

    @Value("\${spring.kafka.consumers.part-updated.name}")
    private val partMasterUpdated: String
) {

    private fun sendMessage(topic: String, message: String) {
        kafkaTemplate.send(topic, message);
    }

    /*
    * Description: Send Vehicle Configuration Change Kafka Message
    * */
    fun sendVehicleConfigChangeKafkaMsg(changeType: String, configId: String, data: Any) {

        try{
            // Generate Json as below
            val message = mapOf(
                "changeType" to changeType,
                "configId" to configId,
                "configurations" to data
            )

            // Convert it into JsonString
            val messageJson = objectMapper.writeValueAsString(message);

            // Send it to sendKafkaMessage
            sendMessage(
                vehicleConfigChange,
                messageJson
            )
        }
        catch (e: Exception) {
            print("Exception While sendVehicleConfigChangeKafkaMsg: $e");
        }

    }

    /*
    * Description: Send Updated Message when Parts Master Updated
    * */
    fun sendPartMasterAddedOrUpdated(changeType: String, partId: String, partDetails: Any){

        try {

            // Generate Json
            val message = mapOf(
                "changeType" to changeType,
                "partId" to partId,
                "details" to partDetails
            )

            // Convert it into JsonString
            val messageJson = objectMapper.writeValueAsString(message);

            // Send Message
            sendMessage(partMasterUpdated, messageJson);

        }
        catch (e: kotlin.Exception) {
            print("Exception While sendPartMasterAddedOrUpdated: $e")
        }

    }

}