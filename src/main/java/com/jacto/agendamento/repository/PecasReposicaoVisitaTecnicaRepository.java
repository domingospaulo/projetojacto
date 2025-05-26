package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.PecasReposicaoVisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PecasReposicaoVisitaTecnicaRepository extends JpaRepository<PecasReposicaoVisitaTecnica, Long> {

    // Método para buscar todos os PecaReposicaoVisitaTecnica por ID da VisitaTecnica
    List<PecasReposicaoVisitaTecnica> findByVisitaTecnica_Id(Long visitaTecnicaId);
}