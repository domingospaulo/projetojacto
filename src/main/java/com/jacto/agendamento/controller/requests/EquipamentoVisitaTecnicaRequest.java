package com.jacto.agendamento.controller.requests;

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
public class EquipamentoVisitaTecnicaRequest {

    @NotNull(message = "Código do equipamento é obrigatório")
    private Integer codigoEquipamento;

    @NotNull(message = "Quantidade do equipamento é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior ou igual a 1")
    private Integer qtde;
}
