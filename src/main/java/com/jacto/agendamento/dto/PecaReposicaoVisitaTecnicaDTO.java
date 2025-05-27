package com.jacto.agendamento.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PecaReposicaoVisitaTecnicaDTO {

    @NotNull(message = "Código da peça de reposição é obrigatório")
    private Integer codigoPecaReposicao;

    @NotNull(message = "Quantidade da peça de reposição é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior ou igual a 1")
    private Integer qtde;
}