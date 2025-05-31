package com.jacto.agendamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        // Retorna uma mensagem personalizada
        String mensagem = "Você não tem permissão para acessar este recurso. Por favor, contacte o administrador.";
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mensagem);
    }

}