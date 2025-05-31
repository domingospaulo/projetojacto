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

    private Long id; 

    private FuncionarioDTO funcionarioDTO;

    private FazendaDTO fazendaDTO;

    private TipoServicoDTO tipoServicoDTO;

    private PrioridadeDTO prioridadeDTO;

    private StatusVisitaDTO statusVisitaDTO;

    private OcorrenciaDTO ocorrenciaDTO;

    private VisitaTecnicaDTO visitaTecnicaReferenciaDTO; 

    private Date dataHoraAgendamento;
    private Date dataHoraVisitaInicioAgendado;
    private Date dataHoraVisitaFimAgendado;
    private Date dataHoraVisitaInicio;
    private Date dataHoraVisitaFim;

    private String observacao;
     
    private Boolean flagReagendamento; 

    private List<EquipamentoVisitaTecnicaDTO> equipamentosVisitaTecnica;

    private List<PecaReposicaoVisitaTecnicaDTO> pecasReposicaoVisitaTecnica;

    private AvaliacaoAtendimentoDTO avaliacaoAtendimentoDTO;
}
