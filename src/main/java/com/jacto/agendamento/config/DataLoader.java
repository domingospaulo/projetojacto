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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

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
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        try {
        // Perfis
        salvarPerfilSeNaoExistir(100, "ACESSO_CLIENTE");
        salvarPerfilSeNaoExistir(200, "ACESSO_FUNCIONARIO_TECNICO");
        salvarPerfilSeNaoExistir(300, "ACESSO_FUNCIONARIO_ADMINISTRADOR");

        // Tipos de serviço
        salvarTipoServicoSeNaoExistir(100, "AVALIAÇÃO TÉCNICA");
        salvarTipoServicoSeNaoExistir(200, "ENTREGA EQUIPAMENTO");
        salvarTipoServicoSeNaoExistir(300, "REPOSICAO PECA");

        // Prioridades
        salvarPrioridadeSeNaoExistir(100, "NORMAL", 1);
        salvarPrioridadeSeNaoExistir(200, "MEDIA", 2);
        salvarPrioridadeSeNaoExistir(300, "ALTA", 3);

        // Cargos
        salvarCargoSeNaoExistir(100, "TECNICO");
        salvarCargoSeNaoExistir(200, "SUPERVISOR");

        // Operações
        salvarOperacaoSeNaoExistir(100, "CADASTRO AGENDAMENTO");
        salvarOperacaoSeNaoExistir(200, "ALTERACAO AGENDAMENTO");
        salvarOperacaoSeNaoExistir(300, "EXCLUSAO AGENDAMENTO");
        salvarOperacaoSeNaoExistir(400, "RECLASSIFICACAO DE AGENDAMENTO AUTOMATICA");

        // Status de visita
        salvarStatusVisitaSeNaoExistir(100, "AGENDADA");
        salvarStatusVisitaSeNaoExistir(200, "REALIZADA COM SUCESSO");
        salvarStatusVisitaSeNaoExistir(300, "REALIZADA COM OCORRENCIA");
        salvarStatusVisitaSeNaoExistir(400, "CANCELADA PELA EMPRESA");
        salvarStatusVisitaSeNaoExistir(500, "CANCELADA PELO CLIENTE");

        // Ocorrências
        salvarOcorrenciaSeNaoExistir(100, "ENDERECO NAO ENCONTRADO");
        salvarOcorrenciaSeNaoExistir(200, "CLIENTE AUSENTE");

        // Equipamentos
        salvarEquipamentoSeNaoExistir(100, "EQUIPAMENTO 01");
        salvarEquipamentoSeNaoExistir(200, "EQUIPAMENTO 02");

         //Peças de reposição
        salvarPecaReposicaoSeNaoExistir(100, "PECA 01");
        salvarPecaReposicaoSeNaoExistir(200, "PECA 02");

        // Pessoas de cliente e funcionário
        salvarPessoaSeNaoExistir("073.990.740-97", "JOSE DA SILVA", "josedasilva@mailnator.com", "98988889999");
        salvarPessoaSeNaoExistir("098.961.300-35", "JOAO SOUZA", "joaosouza@mailnator.com", "98988889999");

        // Funcionários
        salvarFuncionarioSeNaoExistir(2025002L, "073.990.740-97", 100);

        // Clientes
        salvarClienteSeNaoExistir(101011L, "098.961.300-35");
       
       //Estoque de peças de reposição - sera excluido futuramente
        salvarEstoqueEquipamentoSeNaoExistir(100, 2);
        salvarEstoqueEquipamentoSeNaoExistir(200, 1);
         //Estoque de peças de reposição - sera excluido futuramente
        salvarEstoquePecaReposicaoSeNaoExistir(100, 5, 100.50);
        salvarEstoquePecaReposicaoSeNaoExistir(200, 2, 50.99);
         
         salvarUsuarioSeNaoExistir(
             "073.990.740-97", "123456",
             2025002L, null,
             100,
             new Date(), true);

             salvarUsuarioSeNaoExistir(
             "098.961.300-35", "123456", null,
             101011L, 200,
             new Date(), true);

             logger.info("Carga de dados inicial completa!");
        } catch (Exception e) {
            logger.error("Erro durante a carga de dados inicial: {}", e.getMessage(), e);
        }
    }


    private void salvarPerfilSeNaoExistir(Integer codigo, String descricao) {
        Optional<Perfil> perfilOptional = perfilService.buscarPorCodigo(codigo);
        if (perfilOptional.isEmpty()) {
            Perfil perfil = new Perfil(codigo, descricao);
            perfilService.salvar(perfil);
            logger.info("Perfil salvo: Código={}, Descrição={}", codigo, descricao);
        }
    }

    private void salvarTipoServicoSeNaoExistir(Integer codigo, String descricao) {
        Optional<TipoServico> tipoServicoOptional = tipoServicoService.buscarPorCodigo(codigo);
        if (tipoServicoOptional.isEmpty()) {
            TipoServico tipoServico = new TipoServico(codigo, descricao);
            tipoServicoService.salvar(tipoServico);
            logger.info("Tipo Serviço salvo: Código={}, Descrição={}", codigo, descricao);
        }
    }

    private void salvarPrioridadeSeNaoExistir(Integer codigo, String descricao, Integer nivel) {
        Optional<Prioridade> prioridadeOptional = prioridadeService.buscarPorCodigo(codigo);
        if (prioridadeOptional.isEmpty()) {
            Prioridade prioridade = new Prioridade(codigo, descricao, nivel);
            prioridadeService.salvar(prioridade);
             logger.info("Prioridade salva: Código={}, Descrição={}, Nível={}", codigo, descricao, nivel);
        }
    }

    private void salvarCargoSeNaoExistir(Integer codigo, String descricao) {
       Optional<Cargo> cargoOptional = cargoService.buscarPorCodigo(codigo);
        if (cargoOptional.isEmpty()) {
            Cargo cargo = new Cargo(codigo, descricao);
            cargoService.salvar(cargo);
            logger.info("Cargo salvo: Código={}, Descrição={}", codigo, descricao);
       }
    }

    private void salvarOperacaoSeNaoExistir(Integer codigo, String descricao) {
        Optional<Operacao> operacaoOptional = operacaoService.buscarPorCodigo(codigo);
         if (operacaoOptional.isEmpty()) {
            Operacao operacao = new Operacao(codigo, descricao);
            operacaoService.salvar(operacao);
            logger.info("Operação salva: Código={}, Descrição={}", codigo, descricao);
         }
    }

    private void salvarStatusVisitaSeNaoExistir(Integer codigo, String descricao) {
        Optional<StatusVisita> statusVisitaOptional = statusVisitaService.buscarPorCodigo(codigo);
        if (statusVisitaOptional.isEmpty()) {
               StatusVisita statusVisita = new StatusVisita(codigo, descricao);
            statusVisitaService.salvar(statusVisita);
            logger.info("Status Visita salvo: Código={}, Descrição={}", codigo, descricao);
        }
    }

    private void salvarOcorrenciaSeNaoExistir(Integer codigo, String descricao) {
        Optional<Ocorrencia> ocorrenciaOptional = ocorrenciaService.buscarPorCodigo(codigo);
        if (ocorrenciaOptional.isEmpty()) {
            Ocorrencia ocorrencia = new Ocorrencia(codigo, descricao);
            ocorrenciaService.salvar(ocorrencia);
            logger.info("Ocorrência salva: Código={}, Descrição={}", codigo, descricao);
        }
    }
  
 private void salvarEstoqueEquipamentoSeNaoExistir(Integer codigoEquipamento, Integer quantidade) {
        Optional<EstoqueEquipamento> estoqueEquipamentoOptional = estoqueEquipamentoService.buscarPorCodigoEquipamento(codigoEquipamento);
        if (estoqueEquipamentoOptional.isEmpty()) {
            Optional<Equipamento> equipamentoOptional = equipamentoService.buscarPorCodigo(codigoEquipamento);
            if (equipamentoOptional.isPresent()) {
                EstoqueEquipamento estoqueEquipamento = new EstoqueEquipamento(equipamentoOptional.get(), quantidade);
                estoqueEquipamentoService.salvar(estoqueEquipamento);
                logger.info("Estoque Equipamento salvo: codigoEquipamento={}, quantidade={}", codigoEquipamento, quantidade);
            } else {
                logger.warn("Equipamento com código {} não encontrado!", codigoEquipamento);
            }
        }
    }

    private void salvarEstoquePecaReposicaoSeNaoExistir(Integer codigoPecaReposicao, Integer quantidade, Double valor) {
       Optional<EstoquePecaReposicao> estoquePecaReposicaoOptional = estoquePecaReposicaoService.buscarPorCodigoPecaReposicao(codigoPecaReposicao);
        if (estoquePecaReposicaoOptional.isEmpty()) {
           Optional<PecaReposicao> pecaReposicaoOptional = pecaReposicaoService.buscarPorCodigo(codigoPecaReposicao);
             if (pecaReposicaoOptional.isPresent()) {
                PecaReposicao pecaReposicao = pecaReposicaoOptional.get();
                estoquePecaReposicaoService.salvar(new EstoquePecaReposicao(pecaReposicao, quantidade, valor));
                 logger.info("Estoque Peca Reposição salvo: codigoPecaReposicao={}, quantidade={}, valor={}", codigoPecaReposicao, quantidade, valor);
              }else {
                    logger.warn("Peça Reposição com código {} não encontrada!", codigoPecaReposicao);
              }
        }
    }    

    private void salvarEquipamentoSeNaoExistir(Integer codigo, String descricao) {
        Optional<Equipamento> equipamentoOptional = equipamentoService.buscarPorCodigo(codigo);
        if (equipamentoOptional.isEmpty()) {
            Equipamento equipamento = new Equipamento(codigo, descricao);
            equipamentoService.salvar(equipamento);
             logger.info("Equipamento salvo: Código={}, Descrição={}", codigo, descricao);
        }
    }

    private void salvarPecaReposicaoSeNaoExistir(Integer codigo, String descricao) {
         Optional<PecaReposicao> pecaReposicaoOptional = pecaReposicaoService.buscarPorCodigo(codigo);
         if (pecaReposicaoOptional.isEmpty()) {
            PecaReposicao pecaReposicao = new PecaReposicao(codigo, descricao);
            pecaReposicaoService.salvar(pecaReposicao);
            logger.info("Peça Reposição salva: Código={}, Descrição={}", codigo, descricao);
        }
    }

    private void salvarPessoaSeNaoExistir(String cpfCnpj, String nome, String email, String telefone) {
        Optional<Pessoa> pessoaOptional = pessoaService.buscarPorCpfCnpj(cpfCnpj);
        if (pessoaOptional.isEmpty()) {
             Pessoa pessoa = new Pessoa(cpfCnpj, nome, email, telefone);
            pessoaService.salvar(pessoa);
            logger.info("Pessoa salva: CPF/CNPJ={}, Nome={}, Email={}", cpfCnpj, nome, email);
        }
    }

    private void salvarFuncionarioSeNaoExistir(Long matricula, String cpfCnpj, Integer codigoCargo) {
        Optional<Funcionario> funcionarioOptional = funcionarioService.buscarPorMatricula(matricula);
              if (funcionarioOptional.isEmpty()) {
                 Optional<Pessoa> pessoaOptional = pessoaService.buscarPorCpfCnpj(cpfCnpj);
                 Optional<Cargo> cargoOptional = cargoService.buscarPorCodigo(codigoCargo);
                   if (pessoaOptional.isPresent() && cargoOptional.isPresent()) {
                         Pessoa pessoa = pessoaOptional.get();
                         Cargo cargo = cargoOptional.get();
                          Funcionario funcionario = new Funcionario(matricula, pessoa, cargo, new java.util.Date(), true);
                         funcionarioService.salvar(funcionario);
                         logger.info("Funcionário salvo: Matrícula={}, CPF/CNPJ={}, Cargo={}", matricula, cpfCnpj, codigoCargo);
                  }else{
                        logger.warn("Não foi possível salvar o funcionário: pessoa ou cargo não encontrados.");
                     }
            }
    }

    private void salvarClienteSeNaoExistir(Long matricula, String cpfCnpj) {
         Optional<Cliente> clienteOptional = clienteService.buscarPorMatricula(matricula);
         if (clienteOptional.isEmpty()) {
                  Optional<Pessoa> pessoaOptional = pessoaService.buscarPorCpfCnpj(cpfCnpj);
                  if (pessoaOptional.isPresent()) {
                         Pessoa pessoa = pessoaOptional.get();
                        clienteService.salvar(new Cliente(matricula, pessoa, new java.util.Date(), true));
                         logger.info("Cliente salvo: Matrícula={}, CPF/CNPJ={}", matricula, cpfCnpj);
                   }else{
                         logger.warn("Não foi possível salvar o cliente: pessoa não encontrada.");
                  }
         }
    }

   private void salvarUsuarioSeNaoExistir(String login, String senha, Long matriculaFuncionario, Long matriculaCliente, Integer codigoPerfil, Date dataHoraCadastro, Boolean ativo) {
       Optional<Usuario> usuarioOptional = usuarioService.buscarPorLogin(login);
             if (usuarioOptional.isEmpty()) {
                   Funcionario funcionario = null;
                 Cliente cliente = null;

                 if (matriculaFuncionario != null) {
                      Optional<Funcionario> funcionarioOptional = funcionarioService.buscarPorMatricula(matriculaFuncionario);
                      if (funcionarioOptional.isPresent()) {
                               funcionario = funcionarioOptional.get();
                           }else{
                             logger.warn("Não foi possível criar usuário: funcionario não encontrado.");
                            return;
                           }
                  }
                 if (matriculaCliente != null) {
                          Optional<Cliente> clienteOptional = clienteService.buscarPorMatricula(matriculaCliente);
                          if (clienteOptional.isPresent()) {
                                  cliente = clienteOptional.get();
                              }else{
                                  logger.warn("Não foi possível criar usuário: cliente não encontrado.");
                                 return;
                              }
                 }

                 Optional<Perfil> perfilOptional = perfilService.buscarPorCodigo(codigoPerfil);
                  if (perfilOptional.isPresent()) {
                         Perfil perfil = perfilOptional.get();
                           Usuario novoUsuario = new Usuario(login, senha, funcionario, cliente, perfil, dataHoraCadastro, ativo);
                          usuarioService.salvar(novoUsuario);
                           logger.info("Usuário salvo: login={}, perfil={}", login, codigoPerfil);
                   }else {
                           logger.warn("Perfil com código {} não encontrado!", codigoPerfil);
                   }
              }
          }
}