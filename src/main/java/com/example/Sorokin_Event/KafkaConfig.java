package com.example.Sorokin_Event;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaTemplate<Long, KafkaChangeEvent> kafkaTemplate(
            KafkaProperties kafkaProperties
    )
    {
        var props = kafkaProperties.buildProducerProperties(
                new DefaultSslBundleRegistry()
        );
        ProducerFactory<Long, KafkaChangeEvent> producerFactory = new
                DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producerFactory);
    }
}
