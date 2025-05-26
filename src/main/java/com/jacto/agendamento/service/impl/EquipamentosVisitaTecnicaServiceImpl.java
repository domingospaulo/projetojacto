package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.EquipamentosVisitaTecnica;
import com.jacto.agendamento.repository.EquipamentosVisitaTecnicaRepository;
import com.jacto.agendamento.service.EquipamentosVisitaTecnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentosVisitaTecnicaServiceImpl implements EquipamentosVisitaTecnicaService {

    @Autowired
    private EquipamentosVisitaTecnicaRepository repository;

    @Override
    public List<EquipamentosVisitaTecnica> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<EquipamentosVisitaTecnica> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public EquipamentosVisitaTecnica salvar(EquipamentosVisitaTecnica EquipamentosVisitaTecnica) {
        return repository.save(EquipamentosVisitaTecnica);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EquipamentosVisitaTecnica> buscarPorVisitaTecnicaId(Long visitaTecnicaId) {
        return repository.findByVisitaTecnica_Id(visitaTecnicaId);
    }
}