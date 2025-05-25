package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Equipamento;
import com.jacto.agendamento.repository.EquipamentoRepository;
import com.jacto.agendamento.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Override
    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll();
    }

    @Override
    public Optional<Equipamento> buscarPorCodigo(Integer codigo) {
        return equipamentoRepository.findById(codigo);
    }

    @Override
    public Equipamento salvar(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    @Override
    public void deletar(Integer codigo) {
        equipamentoRepository.deleteById(codigo);
    }
}