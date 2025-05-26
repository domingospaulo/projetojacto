package com.jacto.agendamento.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcessoController {
    
    @GetMapping("/")
    public String home() {
        return "A aplicação está rodando!";
    }   

}