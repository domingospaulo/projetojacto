package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.EstoqueEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueEquipamentoRepository extends JpaRepository<EstoqueEquipamento, Long> {

    // Buscar todos os estoques pelo código do equipamento
   Optional<EstoqueEquipamento> findByEquipamento_Codigo(Integer codigoEquipamento);
}