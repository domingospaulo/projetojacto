package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> listarTodos();

    // busca por matrícula (id)
    Optional<Cliente> buscarPorMatricula(Long matricula);                      
    // busca por cpf_cnpj na entidade Pessoa relacionada
    Optional<Cliente> buscarPorCpfCnpj(String cpfCnpj);    

    Cliente salvar(Cliente cliente);

    void deletar(Long matricula);
}