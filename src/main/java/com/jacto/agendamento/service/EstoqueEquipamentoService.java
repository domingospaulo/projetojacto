 package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.EstoqueEquipamento;

import java.util.List;
import java.util.Optional;

public interface EstoqueEquipamentoService {
    List<EstoqueEquipamento> listarTodos();
    Optional<EstoqueEquipamento> buscarPorId(Long id);
    Optional<EstoqueEquipamento> buscarPorCodigoEquipamento(Integer codigoEquipamento);
    EstoqueEquipamento salvar(EstoqueEquipamento estoque);
    void deletar(Long id);
}