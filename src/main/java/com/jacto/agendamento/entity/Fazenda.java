package com.jacto.agendamento.entity;

import java.util.Date;

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
@Table(name = "fazenda")
public class Fazenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // relacionamento com Cliente via matricula_cliente
    @ManyToOne
    @JoinColumn(name = "matricula_cliente", referencedColumnName = "matricula")
    private Cliente cliente;

    @Column(name = "descricao", length = 250)
    private String descricao;

    @Column(name = "endereco", length = 500)
    private String endereco;

    // Campo geográfico (Point) substituído por latitude e longitude devido a problemas do postgis no docker
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "data_hora_cadastro")
    private Date dataHoraCadastro;

    @Column(name = "ativo")
    private Boolean ativo;
}