package com.jacto.agendamento.controller.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    @NotNull(message = "Login é obrigatório")
    private String login;

    @NotNull(message = "Código de perfil é obrigatório")
    private Integer codigoPerfil;

    @NotNull(message = "Senha é obrigatória")
    @Size(max = 250)
    private String senha;

    private Date dataHoraCadastro;
    private Boolean ativo;

    private Long matriculaFuncionario;
    private Long matriculaCliente;
}