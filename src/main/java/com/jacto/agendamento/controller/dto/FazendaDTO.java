package com.jacto.agendamento.controller.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FazendaDTO {

    private Long id;

    private Long matriculaCliente;

    private String descricao;

    private String endereco;

    private Double latitude;

    private Double longitude;

    private Date dataHoraCadastro;

    private Boolean ativo;
}