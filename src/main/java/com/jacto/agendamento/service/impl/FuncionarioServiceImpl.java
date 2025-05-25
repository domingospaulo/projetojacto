package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Funcionario;
import com.jacto.agendamento.repository.FuncionarioRepository;
import com.jacto.agendamento.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Override
    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Funcionario> buscarPorMatricula(Long matricula) {
        return repository.findByMatricula(matricula);
    }

    @Override
    public Optional<Funcionario> buscarPorCpfCnpj(String cpfCnpj) {
        return repository.findByPessoaCpfCnpj(cpfCnpj);
    }

    @Override
    public List<Funcionario> buscarPorCodigoCargo(Integer codigoCargo) {
        return repository.findByCargoCodigo(codigoCargo);
    }

    @Override
    public Funcionario salvar(Funcionario funcionario) {
        return repository.save(funcionario);
    }

    @Override
    public void deletar(Long matricula) {
        repository.deleteById(matricula);
    }
}