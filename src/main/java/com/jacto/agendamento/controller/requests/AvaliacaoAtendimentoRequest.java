package com.jacto.agendamento.controller.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import java.util.Date;
import javax.validation.constraints.Max;
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
public class AvaliacaoAtendimentoRequest {
    
    @NotNull(message = "Avaliação é obrigatória")
    @Min(value = 1, message = "Avaliação deve ser pelo menos 1")
    @Max(value = 5, message = "Avaliação deve ser no máximo 5")
    private Integer avaliacao;

    @javax.validation.constraints.Size(max = 1000, message = "Observação deve ter até 1000 caracteres")
    private String observacao;

    @NotNull(message = "ID da visita técnica é obrigatório")
    private Long idVisitaTecnica;

    private Date dataHoraOperacao;
}