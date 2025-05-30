package com.jacto.agendamento;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import com.jacto.agendamento.fila.KafkaListeners;

@SpringBootApplication
@EnableKafka
public class AgendamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendamentoApplication.class, args);
	}
	
    @Bean
    public ApplicationRunner runner(KafkaListeners kafkaListeners) {
        return args -> {
            kafkaListeners.checkListenerStatus();
        };
    }
}