package com.zup.bank.common.kafka

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaAdmin

class KafkaTopicConfig {

    @Value(value = "\${kafka.bootstrapAddress}")
    private lateinit var bootstrapAddress: String

    @Bean
    fun kafkaAdmin() : KafkaAdmin{
        val configs = HashMap<String, Any>()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress

        return KafkaAdmin(configs)
    }

    @Bean
    fun topic(): NewTopic{
        return NewTopic("transfer",1, 1)
    }

}