package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Fazenda;

import java.util.List;
import java.util.Optional;

public interface FazendaService {
    List<Fazenda> listarTodos();
    Optional<Fazenda> buscarPorId(Long id);
    List<Fazenda> buscarPorMatriculaCliente(Long matriculaCliente);
    Fazenda salvar(Fazenda fazenda);
    void deletar(Long id);
}