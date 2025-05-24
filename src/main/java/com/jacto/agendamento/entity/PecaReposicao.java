package com.jacto.agendamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
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
@Table(name = "peca_reposicao")
public class PecaReposicao {

    @Id
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "descricao", length = 250)
    private String descricao;
}