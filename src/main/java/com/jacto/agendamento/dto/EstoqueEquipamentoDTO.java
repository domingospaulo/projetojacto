package com.jacto.agendamento.dto;

import jakarta.validation.constraints.NotNull;
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
public class EstoqueEquipamentoDTO {

    @NotNull(message = "Código do equipamento é obrigatório")
    private Integer codigoEquipamento;

    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade;
}