package com.jacto.agendamento.dto;

import javax.validation.constraints.NotNull;
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
public class HistoricoAgendamentoVisitaTecnicaDTO {
    
    private Long id; // pode ser nulo ao criar

    @NotNull(message = "ID da visita técnica é obrigatório")
    private Long idVisitaTecnica;

    @NotNull(message = "Código da operação é obrigatório")
    private Integer codigoOperacao;

    @NotNull(message = "Login do usuário é obrigatório")
    private String loginUsuario;

    private Date dataHoraOperacao;

    private String observacao;
}