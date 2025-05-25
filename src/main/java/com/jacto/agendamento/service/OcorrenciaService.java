package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Ocorrencia;

import java.util.List;
import java.util.Optional;

public interface OcorrenciaService {
    List<Ocorrencia> listarTodos();
    Optional<Ocorrencia> buscarPorCodigo(Integer codigo);
    Ocorrencia salvar(Ocorrencia ocorrencia);
    void deletar(Integer codigo);
}