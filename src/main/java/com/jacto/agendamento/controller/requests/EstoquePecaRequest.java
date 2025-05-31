package com.jacto.agendamento.controller.requests;

import javax.validation.constraints.NotNull;
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
public class EstoquePecaRequest {

    @NotNull(message = "Código da peça de reposição é obrigatório")
    private Integer codigoPecaReposicao;

    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade;

    @NotNull(message = "Valor é obrigatório")
    private Double valor;
}