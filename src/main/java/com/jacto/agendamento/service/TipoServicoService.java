package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.TipoServico;

import java.util.List;
import java.util.Optional;

public interface TipoServicoService {
    List<TipoServico> listarTodos();
    Optional<TipoServico> buscarPorCodigo(Integer codigo);
    TipoServico salvar(TipoServico tipoServico);
    void deletar(Integer codigo);
}