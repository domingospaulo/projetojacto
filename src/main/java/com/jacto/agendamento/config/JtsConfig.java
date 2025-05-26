package com.jacto.agendamento.config;


import org.locationtech.jts.geom.GeometryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JtsConfig { 
    private static final Logger logger = LoggerFactory.getLogger(JtsConfig.class);

    @Bean
    public GeometryFactory geometryFactory() {
        logger.info("GeometryFactory bean criado!"); // Adicione esta linha
        return new GeometryFactory();
    }
}