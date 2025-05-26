package com.jacto.agendamento.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull(message = "Matricula do cliente é obrigatória")
    private Long matriculaCliente;

    @Size(max = 250, message = "Descrição deve ter no máximo 250 caracteres")
    private String descricao;

    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    private String endereco;

    @NotNull(message = "Coordenadas são obrigatórias")
    private CoordenadasDTO coordenadas;

    private Date dataHoraCadastro;

    private Boolean ativo;
}