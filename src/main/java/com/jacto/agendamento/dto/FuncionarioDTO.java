package com.jacto.agendamento.dto;

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
public class FuncionarioDTO {

    private Long matricula;

    @NotNull(message = "Código do cargo é obrigatório")
    private Integer codigoCargo;

    @Size(max = 250, message = "Nome deve ter no máximo 250 caracteres")
    private String nome;

    @NotNull(message = "CPF/CNPJ é obrigatório")
    @Size(max = 50, message = "CPF/CNPJ deve ter no máximo 50 caracteres")
    private String cpfCnpj;

    @Size(max = 250, message = "E-mail deve ter no máximo 250 caracteres")
    private String email;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
}