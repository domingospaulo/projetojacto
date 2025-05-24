package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.Fazenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FazendaRepository extends JpaRepository<Fazenda, Long> {

    // Listar todas as fazendas de um cliente pelo número da matrícula
    List<Fazenda> findByClienteMatricula(Long matriculaCliente);
}