package com.jacto.agendamento.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.jacto.agendamento.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(getApiInformation());
        }
    
        private ApiInfo getApiInformation(){
            return new ApiInfo("AGENDAMENTO VISITA TECNICA JACTO",
                                 "API backend para serviços da aplicação de Agendamento de Visitas Tecnicas Jacto.", 
                                 "1.0.0", 
                                 "https://blog.jacto.com.br/", 
                                 new Contact("Jacto", "https://jacto.com", "contato@jacto.com"), 
                                 "Licença", 
                                 "https://jacto.com", 
                                 Collections.emptyList());
               
        }
}