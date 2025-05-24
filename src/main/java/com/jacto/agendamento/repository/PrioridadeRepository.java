package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioridadeRepository extends JpaRepository<Prioridade, Integer> {
}