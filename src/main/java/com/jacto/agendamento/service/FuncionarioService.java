package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioService {
    List<Funcionario> listarTodos();
    Optional<Funcionario> buscarPorMatricula(Long matricula);
    Optional<Funcionario> buscarPorCpfCnpj(String cpfCnpj);
    List<Funcionario> buscarPorCodigoCargo(Integer codigoCargo);
    Funcionario salvar(Funcionario funcionario);
    void deletar(Long matricula);
}