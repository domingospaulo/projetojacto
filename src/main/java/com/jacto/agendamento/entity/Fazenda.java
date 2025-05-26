package com.jacto.agendamento.entity;

import javax.persistence.*;
import org.locationtech.jts.geom.Point;
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

    // Campo geográfico (Point)
    @Column(name = "coordenadas", columnDefinition = "GEOMETRY(Point, 4326)")
    private Point coordenadas;

    @Column(name = "data_hora_cadastro")
    private java.util.Date dataHoraCadastro;

    @Column(name = "ativo")
    private Boolean ativo;
}