package com.jacto.agendamento.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String login;

    private Integer codigoPerfil;

    private String senha;

    private Date dataHoraCadastro;
    private Boolean ativo;

    private Long matriculaFuncionario;
    private Long matriculaCliente;
}