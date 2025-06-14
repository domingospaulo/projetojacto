package com.jacto.agendamento.controller.dto;

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
public class EquipamentoVisitaTecnicaDTO {

    private EquipamentoDTO equipamentoDTO;

    private Integer qtde;
}
