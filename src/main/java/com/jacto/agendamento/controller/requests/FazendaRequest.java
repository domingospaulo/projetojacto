package com.jacto.agendamento.controller.requests;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class FazendaRequest {

    @NotNull(message = "Matricula do cliente é obrigatória")
    private Long matriculaCliente;

    @Size(max = 250, message = "Descrição deve ter no máximo 250 caracteres")
    private String descricao;

    @Size(max = 500, message = "O endereço deve ter no máximo 500 caracteres")
    private String endereco;

    @NotNull(message = "Latitude é obrigatória")
    @Min(value = -90, message = "Latitude deve estar entre -90 e 90")
    @Max(value = 90, message = "Latitude deve estar entre -90 e 90")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    @Min(value = -180, message = "Longitude deve estar entre -180 e 180")
    @Max(value = 180, message = "Longitude deve estar entre -180 e 180")
    private Double longitude;

    private Date dataHoraCadastro;

    private Boolean ativo;
}