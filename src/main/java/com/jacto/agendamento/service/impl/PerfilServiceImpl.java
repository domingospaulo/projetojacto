package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Perfil;
import com.jacto.agendamento.repository.PerfilRepository;
import com.jacto.agendamento.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilRepository repository;

    @Override
    public List<Perfil> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Perfil> buscarPorCodigo(Integer codigo) {
        return repository.findById(codigo);
    }

    @Override
    public Perfil salvar(Perfil perfil) {
        return repository.save(perfil);
    }

    @Override
    public void deletar(Integer codigo) {
        repository.deleteById(codigo);
    }
}