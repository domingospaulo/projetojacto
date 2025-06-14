package com.jacto.agendamento.controller.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class PecaReposicaoRequest {
    @NotNull(message = "Codigo é obrigatória")
    private Integer codigo;

    @NotNull(message = "Descrição é obrigatória")
    @Size(min = 1, max = 250, message = "Descrição deve ter entre 1 e 250 caracteres")
    private String descricao;
}