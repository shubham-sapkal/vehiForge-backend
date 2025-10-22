package com.vehiforge.configurationService.kafka.listeners

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaMessageListeners(

) {


    @KafkaListener(topics = ["\${spring.kafka.consumers.config-change.name}"], groupId = "configurator-$")
    fun listen(message: String) {
        println("Consumed Message for \${spring.kafka.consumers.config-change.name}: $message");
    }

}