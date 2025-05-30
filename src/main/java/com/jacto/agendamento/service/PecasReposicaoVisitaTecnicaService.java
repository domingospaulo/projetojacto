package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.PecasReposicaoVisitaTecnica;
import java.util.List;

public interface PecasReposicaoVisitaTecnicaService {
    PecasReposicaoVisitaTecnica salvar(PecasReposicaoVisitaTecnica entity);
    List<PecasReposicaoVisitaTecnica> buscarPorVisitaTecnica(Long visitaTecnicaId);
}
