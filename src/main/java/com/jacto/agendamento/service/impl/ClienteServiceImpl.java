   package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Cliente;
import com.jacto.agendamento.repository.ClienteRepository;
import com.jacto.agendamento.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorMatricula(Long matricula) {
        return clienteRepository.findById(matricula);
    }

    @Override
    public Optional<Cliente> buscarPorCpfCnpj(String cpfCnpj) {
        return clienteRepository.findByPessoaCpfCnpj(cpfCnpj);
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void deletar(Long matricula) {
        clienteRepository.deleteById(matricula);
    }
}