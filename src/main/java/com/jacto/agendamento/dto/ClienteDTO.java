package com.jacto.agendamento.dto;

import javax.validation.constraints.Email;
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
public class ClienteDTO {

    @NotNull(message = "Matrícula é obrigatória")
    private Long matricula;

    @NotNull(message = "Nome é obrigatório")
    @Size(min = 1, max = 250, message = "Nome deve ter entre 1 e 250 caracteres")
    private String nome;

    @NotNull(message = "CPF/CNPJ é obrigatório")
    @Size(max = 50, message = "CPF/CNPJ deve ter no máximo 50 caracteres")
    private String cpfCnpj;

    @Email(message = "E-mail deve ser válido")
    private String email;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
}