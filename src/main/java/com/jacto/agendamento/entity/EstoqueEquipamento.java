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
@Table(name = "estoque_equipamento")
public class EstoqueEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Chave estrangeira para Equipamento
    @ManyToOne
    @JoinColumn(name = "codigo_equipamento", referencedColumnName = "codigo")
    private Equipamento equipamento;

    @Column(name = "quantidade")
    private Integer quantidade;

    public EstoqueEquipamento(Equipamento equipamento, Integer quantidade) {
        this.equipamento = equipamento;
        this.quantidade = quantidade;
    }
}