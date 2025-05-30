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
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "login", length = 100)
    private String login;

    @Column(name = "senha", length = 250)
    private String senha;

    // relacionamento com Funcionario
    @ManyToOne
    @JoinColumn(name = "matricula_funcionario", referencedColumnName = "matricula")
    private Funcionario funcionario;

    // relacionamento com Cliente
    @ManyToOne
    @JoinColumn(name = "matricula_cliente", referencedColumnName = "matricula")
    private Cliente cliente;

    // relação com Perfil via codigo_perfil
    @ManyToOne
    @JoinColumn(name = "codigo_perfil", referencedColumnName = "codigo")
    private Perfil perfil;

    @Column(name = "data_hora_cadastro")
    private Date dataHoraCadastro;

    @Column(name = "ativo")
    private Boolean ativo;
}