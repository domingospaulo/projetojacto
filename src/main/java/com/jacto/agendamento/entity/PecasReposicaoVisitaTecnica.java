package com.jacto.agendamento.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pecas_reposicao_visita_tecnica")
public class PecasReposicaoVisitaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_visita_tecnica", nullable = false)
    private VisitaTecnica visitaTecnica;

    @ManyToOne
    @JoinColumn(name = "id_peca_reposicao", nullable = false)
    private PecaReposicao pecaReposicao;

    @Column(name = "qtde")
    private Integer qtde;
}
