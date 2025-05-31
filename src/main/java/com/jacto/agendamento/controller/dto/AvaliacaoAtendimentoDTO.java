package com.jacto.agendamento.controller.dto;

import java.util.Date;
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
public class AvaliacaoAtendimentoDTO {

    private Long id;

    private Integer avaliacao;

    private String observacao;

    private VisitaTecnicaDTO visitaTecnicaDTO;

    private Date dataHoraOperacao;
}