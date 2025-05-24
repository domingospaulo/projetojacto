package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.EstoquePecaReposicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstoquePecaReposicaoRepository extends JpaRepository<EstoquePecaReposicao, Long> {

    // Buscar todos os estoques pelo código da peça de reposição
    List<EstoquePecaReposicao> findByPecaReposicaoCodigo(Integer codigoPecaReposicao);
}