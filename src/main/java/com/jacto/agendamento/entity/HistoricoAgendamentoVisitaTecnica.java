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
@ToString(exclude = {"visitaTecnica", "operacao"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historico_agendamento_visita_tecnica")
public class HistoricoAgendamentoVisitaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Relacionamento com VisitaTecnica (id_visita_tecnica)
    @ManyToOne
    @JoinColumn(name = "id_visita_tecnica", referencedColumnName = "id")
    private VisitaTecnica visitaTecnica;

    // Relacionamento com Operacao (codigo_operacao)
    @ManyToOne
    @JoinColumn(name = "codigo_operacao", referencedColumnName = "codigo")
    private Operacao operacao;

    @ManyToOne
    @JoinColumn(name = "login_usuario", referencedColumnName = "login")
    private Usuario usuario;

    @Column(name = "data_hora_operacao")
    private Date dataHoraOperacao;

    @Column(name = "observacao", length = 1000)
    private String observacao;
}