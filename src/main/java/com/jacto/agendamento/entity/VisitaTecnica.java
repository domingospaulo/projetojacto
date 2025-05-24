package com.jacto.agendamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@ToString(exclude = {"usuario", "funcionario", "fazenda", "tipoServico", "prioridade", "statusVisita", "ocorrencia", "visitaRef"})
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
    @JoinColumn(name = "login_usuario", referencedColumnName = "login")
    private Usuario usuario;

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
}