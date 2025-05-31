package com.jacto.agendamento.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {

    private Long matricula;

    private Integer codigoCargo;

    private String nome;

    private String cpfCnpj;

    private String email;

    private String telefone;
}