package com.jacto.agendamento.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Getter
@Setter
@ToString(exclude = {"visita"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacao_atendimento")
public class AvaliacaoAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_visita_tecnica", referencedColumnName = "id")
    private VisitaTecnica visita; // relação com visita

    @Column(name = "avaliacao")
    private Integer avaliacao;

    @Column(name = "observacao", length = 500)
    private String observacao;

    @Column(name = "data_hora_operacao")
    private Date dataHoraOperacao;
}