package com.jacto.agendamento.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VisitaTecnicaDTO {

    private Long id; // opcional, será nulo para criar nova

    @NotNull(message = "Matricula do funcionário é obrigatória")
    private Long matriculaFuncionario;

    @NotNull(message = "ID da fazenda é obrigatório")
    private Long idFazenda;

    @NotNull(message = "ID do tipo de serviço é obrigatório")
    private Integer idTipoServico;

    @NotNull(message = "ID da prioridade é obrigatório")
    private Integer idPrioridade;

    @NotNull(message = "ID do status da visita é obrigatório")
    private Integer idStatusVisita;

    @NotNull(message = "Código de ocorrência é obrigatório")
    private Integer codigoOcorrencia;

    private Long idVisitaTecnica; // auto relacionamento, opcional

    private Date dataHoraAgendamento;
    private Date dataHoraVisitaInicioAgendado;
    private Date dataHoraVisitaFimAgendado;
    private Date dataHoraVisitaInicio;
    private Date dataHoraVisitaFim;

    @Size(max = 500, message = "Observação deve ter no máximo 500 caracteres")
    private String observacao;
     
    private Boolean flagReagendamento; 

    @Valid // Habilita a validação em cascata
    private List<EquipamentoVisitaTecnicaDTO> equipamentosVisitaTecnica = new ArrayList<>();

    @Valid // Habilita a validação em cascata
    private List<PecaReposicaoVisitaTecnicaDTO> pecasReposicaoVisitaTecnica = new ArrayList<>();

}
