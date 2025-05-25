package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.VisitaTecnica;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VisitaTecnicaService {

    List<VisitaTecnica> listarTodos();

    Optional<VisitaTecnica> buscarPorId(Long id);

    VisitaTecnica salvar(VisitaTecnica visita);

    void deletar(Long id);

    List<VisitaTecnica> buscarPorMatriculaFuncionario(Long matriculaFuncionario);

    List<VisitaTecnica> buscarPorIdFazenda(Long idFazenda);

    List<VisitaTecnica> buscarPorCodigoTipoServico(Integer codigoTipoServico);

    List<VisitaTecnica> buscarPorCodigoPrioridade(Integer codigoPrioridade);

    List<VisitaTecnica> buscarPorCodigoStatusVisita(Integer codigoStatusVisita);

    List<VisitaTecnica> buscarPorCodigoOcorrencia(Integer codigoOcorrencia);

    List<VisitaTecnica> buscarPorDataHoraVisitaInicioAgendadoEntre(Date inicio, Date fim);
}