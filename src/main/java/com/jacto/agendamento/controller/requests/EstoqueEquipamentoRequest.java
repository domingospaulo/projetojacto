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
public class EstoqueEquipamentoRequest {

    @NotNull(message = "Código do equipamento é obrigatório")
    private Integer codigoEquipamento;

    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade;
}