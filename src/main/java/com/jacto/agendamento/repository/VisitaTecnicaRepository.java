package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.VisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;

@Repository
public interface VisitaTecnicaRepository extends JpaRepository<VisitaTecnica, Long> {

    // Buscar por matrícula do funcionário
    List<VisitaTecnica> findByFuncionarioMatricula(Long matriculaFuncionario);

    // Buscar por id da fazenda
    List<VisitaTecnica> findByFazendaId(Long idFazenda);

    // Buscar por código de tipo de serviço
    List<VisitaTecnica> findByTipoServicoCodigo(Integer codigoTipoServico);

    // Buscar por código de prioridade
    List<VisitaTecnica> findByPrioridadeCodigo(Integer codigoPrioridade);

    // Buscar por código de status da visita
    List<VisitaTecnica> findByStatusVisitaCodigo(Integer codigoStatusVisita);

    // Buscar por código de ocorrência
    List<VisitaTecnica> findByOcorrenciaCodigo(Integer codigoOcorrencia);

    // Buscar visitas cuja data_hora_visita_inicio esteja entre duas datas
    List<VisitaTecnica> findByDataHoraVisitaInicioBetween(Date inicio, Date fim);
}
