package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.EquipamentosVisitaTecnica;

import java.util.List;
import java.util.Optional;

public interface EquipamentosVisitaTecnicaService {

    List<EquipamentosVisitaTecnica> listarTodos();

    Optional<EquipamentosVisitaTecnica> buscarPorId(Long id);

    EquipamentosVisitaTecnica salvar(EquipamentosVisitaTecnica equipamentoVisitaTecnica);

    void deletar(Long id);

    List<EquipamentosVisitaTecnica> buscarPorVisitaTecnicaId(Long visitaTecnicaId);
}