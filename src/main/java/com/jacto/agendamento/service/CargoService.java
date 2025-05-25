package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Cargo;

import java.util.List;
import java.util.Optional;

public interface CargoService {

    List<Cargo> listarTodos();

    Optional<Cargo> buscarPorCodigo(Integer codigo);

    Cargo salvar(Cargo cargo);

    void deletar(Integer codigo);
}
