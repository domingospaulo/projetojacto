package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {
    List<Pessoa> listarTodos();
    Optional<Pessoa> buscarPorCpfCnpj(String cpfCnpj);
    Pessoa salvar(Pessoa pessoa);
    void deletar(String cpfCnpj);
}