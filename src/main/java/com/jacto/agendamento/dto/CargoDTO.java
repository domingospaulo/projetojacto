package com.jacto.agendamento.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CargoDTO {

    private Integer codigo;

    @NotNull(message = "Descrição é obrigatória")
    @Size(min = 1, max = 250, message = "Descrição deve ter entre 1 e 250 caracteres")
    private String descricao;
}