package com.jacto.agendamento.service;

public interface EmailService {
    
     public void enviarEmail(String para, String assunto, String texto) throws Exception;
}
