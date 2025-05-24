package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // Buscar por matrícula do funcionário
    Optional<Funcionario> findByMatricula(Long matricula);
    
    // Buscar todos os funcionários por código do cargo
    List<Funcionario> findByCargoCodigo(Integer codigoCargo);
    
    // Buscar por CPF/CNPJ da pessoa associada
    Optional<Funcionario> findByPessoaCpfCnpj(String cpfCnpj);
}