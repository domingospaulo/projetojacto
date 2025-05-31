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
public class EstoquePecaReposicaoDTO {

    private PecaReposicaoDTO pecaReposicaoDTO;

    private Integer quantidade;

    private Double valor;
}