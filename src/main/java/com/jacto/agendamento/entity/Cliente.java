package com.jacto.agendamento.entity;

import javax.persistence.*;
import java.util.Date;
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
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "matricula")
    private Long matricula;

    @OneToOne
    @JoinColumn(name = "cpf_cnpj_pessoa", referencedColumnName = "cpf_cnpj")
    private Pessoa pessoa;

    @Column(name = "data_hora_cadastro")
    private Date dataHoraCadastro;

    @Column(name = "ativo")
    private Boolean ativo;
}