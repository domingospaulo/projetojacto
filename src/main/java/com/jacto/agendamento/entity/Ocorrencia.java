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
@Table(name = "ocorrencia")
public class Ocorrencia {

    @Id
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "descricao", length = 250)
    private String descricao;
}