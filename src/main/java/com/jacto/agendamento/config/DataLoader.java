package com.jacto.agendamento.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.jacto.agendamento.entity.Cargo;
import com.jacto.agendamento.entity.Cliente;
import com.jacto.agendamento.entity.Equipamento;
import com.jacto.agendamento.entity.EstoqueEquipamento;
import com.jacto.agendamento.entity.EstoquePecaReposicao;
import com.jacto.agendamento.entity.Funcionario;
import com.jacto.agendamento.entity.Ocorrencia;
import com.jacto.agendamento.entity.Operacao;
import com.jacto.agendamento.entity.PecaReposicao;
import com.jacto.agendamento.entity.Perfil;
import com.jacto.agendamento.entity.Pessoa;
import com.jacto.agendamento.entity.Prioridade;
import com.jacto.agendamento.entity.StatusVisita;
import com.jacto.agendamento.entity.TipoServico;
import com.jacto.agendamento.entity.Usuario;
// Importando seus serviços
import com.jacto.agendamento.service.CargoService;
import com.jacto.agendamento.service.ClienteService;
import com.jacto.agendamento.service.EquipamentoService;
import com.jacto.agendamento.service.EstoqueEquipamentoService;
import com.jacto.agendamento.service.EstoquePecaReposicaoService;
import com.jacto.agendamento.service.FuncionarioService;
import com.jacto.agendamento.service.OcorrenciaService;
import com.jacto.agendamento.service.OperacaoService;
import com.jacto.agendamento.service.PecaReposicaoService;
import com.jacto.agendamento.service.PerfilService;
import com.jacto.agendamento.service.PessoaService;
import com.jacto.agendamento.service.PrioridadeService;
import com.jacto.agendamento.service.StatusVisitaService;
import com.jacto.agendamento.service.TipoServicoService;
import com.jacto.agendamento.service.UsuarioService;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired private PerfilService perfilService;
    @Autowired private TipoServicoService tipoServicoService;
    @Autowired private PrioridadeService prioridadeService;
    @Autowired private CargoService cargoService;
    @Autowired private OperacaoService operacaoService;
    @Autowired private StatusVisitaService statusVisitaService;
    @Autowired private OcorrenciaService ocorrenciaService;
    @Autowired private PessoaService pessoaService;
    @Autowired private FuncionarioService funcionarioService;
    @Autowired private ClienteService clienteService;
    @Autowired private PecaReposicaoService pecaReposicaoService;
    @Autowired private EstoquePecaReposicaoService estoquePecaReposicaoService;
    @Autowired private EquipamentoService equipamentoService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private EstoqueEquipamentoService estoqueEquipamentoService;    

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Perfis
        perfilService.salvar(new Perfil(100, "ACESSO_CLIENTE"));
        perfilService.salvar(new Perfil(200, "ACESSO_FUNCIONARIO_TECNICO"));
        perfilService.salvar(new Perfil(300, "ACESSO_FUNCIONARIO_ADMINISTRADOR"));

        // Tipos de serviço
        tipoServicoService.salvar(new TipoServico(100, "AVALIAÇÃO TÉCNICA"));
        tipoServicoService.salvar(new TipoServico(200, "ENTREGA EQUIPAMENTO"));
        tipoServicoService.salvar(new TipoServico(300, "REPOSICAO PECA"));

        // Prioridades
        prioridadeService.salvar(new Prioridade(100, "NORMAL", 1));
        prioridadeService.salvar(new Prioridade(200, "MEDIA", 2));
        prioridadeService.salvar(new Prioridade(300, "ALTA", 3));

        // Cargos
        cargoService.salvar(new Cargo(100, "TECNICO"));
        cargoService.salvar(new Cargo(200, "SUPERVISOR"));

        // Operações
        operacaoService.salvar(new Operacao(100, "CADASTRO AGENDAMENTO"));
        operacaoService.salvar(new Operacao(200, "ALTERACAO AGENDAMENTO"));
        operacaoService.salvar(new Operacao(300, "EXCLUSAO AGENDAMENTO"));
        operacaoService.salvar(new Operacao(400, "RECLASSIFICACAO DE AGENDAMENTO AUTOMATICA"));

        // Status de visita
        statusVisitaService.salvar(new StatusVisita(100, "AGENDADA"));
        statusVisitaService.salvar(new StatusVisita(200, "REALIZADA COM SUCESSO"));
        statusVisitaService.salvar(new StatusVisita(300, "REALIZADA COM OCORRENCIA"));
        statusVisitaService.salvar(new StatusVisita(400, "CANCELADA PELA EMPRESA"));
        statusVisitaService.salvar(new StatusVisita(500, "CANCELADA PELO CLIENTE"));

        // Ocorrências
        ocorrenciaService.salvar(new Ocorrencia(100, "ENDERECO NAO ENCONTRADO"));
        ocorrenciaService.salvar(new Ocorrencia(200, "CLIENTE AUSENTE"));

        // Equipamentos
        equipamentoService.salvar(new Equipamento(100, "EQUIPAMENTO 01"));
        equipamentoService.salvar(new Equipamento(200, "EQUIPAMENTO 02"));

       // Estoque de peças de reposição
        estoqueEquipamentoService.salvar(new EstoqueEquipamento(equipamentoService.buscarPorCodigo(100).get(), 2));
        estoqueEquipamentoService.salvar(new EstoqueEquipamento(equipamentoService.buscarPorCodigo(200).get(), 1));

        // Peças de reposição
        pecaReposicaoService.salvar(new PecaReposicao(100, "PECA 01"));
        pecaReposicaoService.salvar(new PecaReposicao(200, "PECA 02"));

       // Estoque de peças de reposição
        estoquePecaReposicaoService.salvar(new EstoquePecaReposicao(pecaReposicaoService.buscarPorCodigo(100).get(), 5, 100.50));
        estoquePecaReposicaoService.salvar(new EstoquePecaReposicao(pecaReposicaoService.buscarPorCodigo(200).get(), 2, 50.99));

        // Pessoas de cliente e funcionário
        pessoaService.salvar(new Pessoa("073.990.740-97", "JOSE DA SILVA", "josedasilva@mailnator.com", "98988889999"));
        pessoaService.salvar(new Pessoa("098.961.300-35", "JOAO SOUZA", "joaosouza@mailnator.com", "98988889999"));

        // Funcionários
        funcionarioService.salvar(new Funcionario(2025002L, pessoaService.buscarPorCpfCnpj("073.990.740-97").get(), new Cargo(100, "TECNICO"), new java.util.Date(), true));

        // Clientes
        clienteService.salvar(new Cliente(101011L, pessoaService.buscarPorCpfCnpj("073.990.740-97").get(), new java.util.Date(), true));

        // Usuários
        usuarioService.salvar(new Usuario(
            "073.990.740-97", "123456", 
            funcionarioService.buscarPorMatricula(2025001L).get(), null, 
            perfilService.buscarPorCodigo(100).get(),  
            new java.util.Date(), true));

        // Usuários
        usuarioService.salvar(new Usuario(
            "098.961.300-35", "123456", null, clienteService.buscarPorMatricula(101011L).get(),
            perfilService.buscarPorCodigo(200).get(),
            new java.util.Date(), true));
    }

}
