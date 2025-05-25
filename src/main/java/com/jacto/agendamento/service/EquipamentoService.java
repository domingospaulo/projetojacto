package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Equipamento;

import java.util.List;
import java.util.Optional;

public interface EquipamentoService {
    List<Equipamento> listarTodos();
    Optional<Equipamento> buscarPorCodigo(Integer codigo);
    Equipamento salvar(Equipamento equipamento);
    void deletar(Integer codigo);
}
