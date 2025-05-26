package com.jacto.agendamento.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacto.agendamento.service.LocalizacaoService;

public class LocalizacaoServiceImpl implements LocalizacaoService {

    private static final Logger logger = LoggerFactory.getLogger(LocalizacaoServiceImpl.class);

    @Value("${nominatim.api.url}")
    private String nominatimApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getDetalhesLocalizacao(double latitude, double longitude) {
        try {
            String url = nominatimApiUrl
                    .replace("{latitude}", String.valueOf(latitude))
                    .replace("{longitude}", String.valueOf(longitude));

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = response.getBody();
                JsonNode root = objectMapper.readTree(jsonResponse);

                // Tenta obter o display_name diretamente
                JsonNode displayNameNode = root.get("display_name");

                if (displayNameNode != null) {
                    return displayNameNode.asText();
                } else {
                    logger.warn("display_name não encontrado para latitude: {}, longitude: {}", latitude, longitude);
                    return "Localização não encontrada";
                }
            } else {
                logger.error("Erro ao obter detalhes da localização. Código de status: {}", response.getStatusCode());
                return "Erro ao obter detalhes da localização";
            }

        } catch (HttpClientErrorException e) {
            logger.error("Erro no cliente HTTP: {}", e.getStatusCode());
            return "Erro ao obter detalhes da localização";
        } catch (HttpServerErrorException e) {
            logger.error("Erro no servidor HTTP: {}", e.getStatusCode());
            return "Erro ao obter detalhes da localização";
        } catch (IOException e) {
            logger.error("Erro de IO ao processar a resposta: {}", e.getMessage(), e);
            return "Erro ao obter detalhes da localização";
        } catch (Exception e) {
            logger.error("Erro inesperado ao obter detalhes da localização: {}", e.getMessage(), e);
            return "Erro ao obter detalhes da localização";
        }
    }
}