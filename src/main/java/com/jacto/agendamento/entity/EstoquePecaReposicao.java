package com.jacto.agendamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estoque_peca_reposicao")
public class EstoquePecaReposicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Chave estrangeira para PecaReposicao
    @ManyToOne
    @JoinColumn(name = "codigo_peca_reposicao", referencedColumnName = "codigo")
    private PecaReposicao pecaReposicao;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor")
    private Double valor;
}