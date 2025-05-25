package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Cargo;
import com.jacto.agendamento.repository.CargoRepository;
import com.jacto.agendamento.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Override
    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }

    @Override
    public Optional<Cargo> buscarPorCodigo(Integer codigo) {
        return cargoRepository.findById(codigo);
    }

    @Override
    public Cargo salvar(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    @Override
    public void deletar(Integer codigo) {
        cargoRepository.deleteById(codigo);
    }
}