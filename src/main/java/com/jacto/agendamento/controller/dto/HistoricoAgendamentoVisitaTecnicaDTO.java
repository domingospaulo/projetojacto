package com.jacto.agendamento.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoAgendamentoVisitaTecnicaDTO {
    
    private Long id;

    private Long idVisitaTecnica;

    private Integer codigoOperacao;

    private String loginUsuario;

    private Date dataHoraOperacao;

    private String observacao;
}