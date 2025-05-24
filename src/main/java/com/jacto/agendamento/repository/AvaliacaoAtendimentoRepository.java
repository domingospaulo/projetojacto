package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.AvaliacaoAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

@Repository
public interface AvaliacaoAtendimentoRepository extends JpaRepository<AvaliacaoAtendimento, Long> {

    // Busca avaliações pelo id_visita_tecnica
    List<AvaliacaoAtendimento> findByVisitaId(Long idVisitaTecnica);

    // Busca avaliações pelo período da data_hora_operacao
    List<AvaliacaoAtendimento> findByDataHoraOperacaoBetween(Date inicio, Date fim);

    // Busca avaliações pelo matricula do funcionário associado à visita técnica
    List<AvaliacaoAtendimento> findByVisitaFuncionarioMatricula(Long matricula);

    // Busca a média das avaliações de um funcionário pelo matricula
    @Query("select AVG(a.avaliacao) from AvaliacaoAtendimento a where a.visita.funcionario.matricula = :matricula")
    Double findAverageAvaliacaoByFuncionarioMatricula(@Param("matricula") Long matricula);
}