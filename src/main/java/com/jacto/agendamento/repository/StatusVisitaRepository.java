package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.StatusVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusVisitaRepository extends JpaRepository<StatusVisita, Integer> {
}