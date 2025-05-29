package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.EstoqueEquipamento;
import com.jacto.agendamento.repository.EstoqueEquipamentoRepository;
import com.jacto.agendamento.service.EstoqueEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Optional;
                                                                                                                                                                                                         
@Service
public class EstoqueEquipamentoServiceImpl implements EstoqueEquipamentoService {

    @Autowired                                                                                                                                                                                  
    private EstoqueEquipamentoRepository estoqueRepository;

    @Override
    public List<EstoqueEquipamento> listarTodos() {
        return estoqueRepository.findAll();
    }

    @Override
    public Optional<EstoqueEquipamento> buscarPorId(Long id) {
        return estoqueRepository.findById(id);
    }

    @Override
    public Optional<EstoqueEquipamento> buscarPorCodigoEquipamento(Integer codigoEquipamento) {
        return estoqueRepository.findByEquipamento_Codigo(codigoEquipamento);
    }

    @Override
    public EstoqueEquipamento salvar(EstoqueEquipamento estoque) {
        return estoqueRepository.save(estoque);
    }

    @Override
    public void deletar(Long id) {
        estoqueRepository.deleteById(id);
    }
}