package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Buscar por matrícula (id)
    Optional<Cliente> findByMatricula(Long matricula);

    // Buscar por cpf_cnpj na entidade Pessoa relacionada
    Optional<Cliente> findByPessoaCpfCnpj(String cpfCnpj);
}