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
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @Column(name = "matricula")
    private Long matricula;

    @OneToOne
    @JoinColumn(name = "cpf_cnpj_pessoa", referencedColumnName = "cpf_cnpj")
    private Pessoa pessoa;

    // relação com Cargo via id_cargo
    @ManyToOne
    @JoinColumn(name = "codigo_cargo", referencedColumnName = "codigo")
    private Cargo cargo;

    @Column(name = "data_hora_cadastro")
    private Date dataHoraCadastro;

    @Column(name = "ativo")
    private Boolean ativo;
}