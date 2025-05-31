package com.jacto.agendamento.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"funcionario", "fazenda", "tipoServico", "prioridade", "statusVisita", "ocorrencia", "visitaRef"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visita_tecnica")
public class VisitaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // relacionamentos 1:N 
    @ManyToOne
    @JoinColumn(name = "matricula_funcionario", referencedColumnName = "matricula")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_fazenda", referencedColumnName = "id")
    private Fazenda fazenda;

    @ManyToOne
    @JoinColumn(name = "id_tipo_servico", referencedColumnName = "codigo")
    private TipoServico tipoServico;

    @ManyToOne
    @JoinColumn(name = "id_prioridade", referencedColumnName = "codigo")
    private Prioridade prioridade;

    @ManyToOne
    @JoinColumn(name = "id_status_visita", referencedColumnName = "codigo")
    private StatusVisita statusVisita;

    @ManyToOne
    @JoinColumn(name = "codigo_ocorrencia", referencedColumnName = "codigo")
    private Ocorrencia ocorrencia;

    // campo de auto-relacionamento 1:1
    @OneToOne
    @JoinColumn(name = "id_visita_tecnica", referencedColumnName = "id")
    private VisitaTecnica visitaRef;

    // datas
    @Column(name = "data_hora_agendamento")
    private Date dataHoraAgendamento;

    @Column(name = "data_hora_visita_inicio_agendado")
    private Date dataHoraVisitaInicioAgendado;

    @Column(name = "data_hora_visita_fim_agendado")
    private Date dataHoraVisitaFimAgendado;

    @Column(name = "data_hora_visita_inicio")
    private Date dataHoraVisitaInicio;

    @Column(name = "data_hora_visita_fim")
    private Date dataHoraVisitaFim;

    @Column(name = "observacao", length = 500)
    private String observacao;

    @Column(name = "flag_reagendamento")
    private Boolean flagReagendamento;

     // Relacionamento com HistoricoAgendamentoVisitaTecnica
    @OneToMany(mappedBy = "visitaTecnica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoAgendamentoVisitaTecnica> historicoAgendamentoVisitaTecnicas = new ArrayList<>();

     // Relacionamento com EquipamentosVisitaTecnica
    @OneToMany(mappedBy = "visitaTecnica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipamentosVisitaTecnica> equipamentosVisitaTecnica = new ArrayList<>();

    // Relacionamento com PecasReposicaoVisitaTecnica
    @OneToMany(mappedBy = "visitaTecnica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PecasReposicaoVisitaTecnica> pecasReposicaoVisitaTecnica = new ArrayList<>();
 
    // Relacionamento com AvaliacaoAtendimento
    @OneToOne(mappedBy = "visita", cascade = CascadeType.ALL, orphanRemoval = true)
    private AvaliacaoAtendimento avaliacaoAtendimento;
     
}