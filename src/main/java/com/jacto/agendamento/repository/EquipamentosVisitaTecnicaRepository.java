package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.EquipamentosVisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipamentosVisitaTecnicaRepository extends JpaRepository<EquipamentosVisitaTecnica, Long> {

    // Método para buscar todos os EquipamentoVisitaTecnica por ID da VisitaTecnica
    List<EquipamentosVisitaTecnica> findByVisitaTecnica_Id(Long visitaTecnicaId);

}