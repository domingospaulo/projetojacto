package com.jacto.agendamento.controller;

import com.jacto.agendamento.config.JwtService;
import com.jacto.agendamento.service.UsuarioService;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AcessoController {
    

    @Autowired
    private UsuarioService usuarioService; // serviço para buscar usuário e senha

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Validar usuário/senha (implemente seu método de validação)
        boolean valido = usuarioService.validarCredenciais(request.getLogin(), request.getSenha());
        if (valido) {
            // Buscar perfil do usuário (pode buscar do banco)
            String perfilCodigo = usuarioService.buscarPerfilPorLogin(request.getLogin());

            String token = jwtService.generateToken(request.getLogin(), perfilCodigo);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @Getter
    @Setter    
    static class LoginRequest {
        private String login;
        private String senha;
        // getters e setters
    }

    static class JwtResponse {
        private String token;
        public JwtResponse(String token) { this.token = token; }
        public String getToken() { return token; }
    }

}