package com.jacto.agendamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
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

    // relação com Perfil via id_perfil
    @ManyToOne
    @JoinColumn(name = "id_perfil", referencedColumnName = "codigo")
    private Perfil perfil;

    @Column(name = "data_hora_cadastro")
    private Date dataHoraCadastro;

    @Column(name = "ativo")
    private Boolean ativo;
}