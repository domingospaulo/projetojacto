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
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @Column(name = "cpf_cnpj", length = 50)
    private String cpfCnpj;

    @Column(name = "nome", length = 250)
    private String nome;

    @Column(name = "email", length = 250)
    private String email;

    @Column(name = "telefone")
    private Long telefone;
}