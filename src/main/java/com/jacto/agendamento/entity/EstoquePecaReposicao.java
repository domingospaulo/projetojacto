package com.jacto.agendamento.entity;

import javax.persistence.*;
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

    public EstoquePecaReposicao(PecaReposicao pecaReposicao, Integer quantidade, Double valor) {
        this.pecaReposicao = pecaReposicao;
        this.quantidade = quantidade;
        this.valor = valor;
    }
}