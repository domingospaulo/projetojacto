package com.jacto.agendamento.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VisitaTecnicaDTO {

    private Long id; // opcional, será nulo para criar nova

    private Long matriculaFuncionario;

    private Long idFazenda;

    private Integer idTipoServico;

    private Integer idPrioridade;

    private Integer idStatusVisita;

    private Integer codigoOcorrencia;

    private Long idVisitaTecnica; // auto relacionamento, opcional

    private Date dataHoraAgendamento;
    private Date dataHoraVisitaInicioAgendado;
    private Date dataHoraVisitaFimAgendado;
    private Date dataHoraVisitaInicio;
    private Date dataHoraVisitaFim;

    private String observacao;
     
    private Boolean flagReagendamento; 

    private List<EquipamentoVisitaTecnicaDTO> equipamentosVisitaTecnica;

    private List<PecaReposicaoVisitaTecnicaDTO> pecasReposicaoVisitaTecnica;
}
