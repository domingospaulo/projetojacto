package com.jacto.agendamento.config;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jacto.agendamento.entity.Cargo;
import com.jacto.agendamento.entity.Cliente;
import com.jacto.agendamento.entity.Equipamento;
import com.jacto.agendamento.entity.EquipamentosVisitaTecnica;
import com.jacto.agendamento.entity.EstoqueEquipamento;
import com.jacto.agendamento.entity.EstoquePecaReposicao;
import com.jacto.agendamento.entity.Fazenda;
import com.jacto.agendamento.entity.Funcionario;
import com.jacto.agendamento.entity.HistoricoAgendamentoVisitaTecnica;
import com.jacto.agendamento.entity.Ocorrencia;
import com.jacto.agendamento.entity.Operacao;
import com.jacto.agendamento.entity.PecaReposicao;
import com.jacto.agendamento.entity.PecasReposicaoVisitaTecnica;
import com.jacto.agendamento.entity.Perfil;
import com.jacto.agendamento.entity.Pessoa;
import com.jacto.agendamento.entity.Prioridade;
import com.jacto.agendamento.entity.StatusVisita;
import com.jacto.agendamento.entity.TipoServico;
import com.jacto.agendamento.entity.Usuario;
import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.service.CargoService;
import com.jacto.agendamento.service.ClienteService;
import com.jacto.agendamento.service.EquipamentoService;
import com.jacto.agendamento.service.EquipamentosVisitaTecnicaService;
import com.jacto.agendamento.service.EstoqueEquipamentoService;
import com.jacto.agendamento.service.EstoquePecaReposicaoService;
import com.jacto.agendamento.service.FazendaService;
import com.jacto.agendamento.service.FuncionarioService;
import com.jacto.agendamento.service.HistoricoAgendamentoVisitaTecnicaService;
import com.jacto.agendamento.service.OcorrenciaService;
import com.jacto.agendamento.service.OperacaoService;
import com.jacto.agendamento.service.PecaReposicaoService;
import com.jacto.agendamento.service.PecasReposicaoVisitaTecnicaService;
import com.jacto.agendamento.service.PerfilService;
import com.jacto.agendamento.service.PessoaService;
import com.jacto.agendamento.service.PrioridadeService;
import com.jacto.agendamento.service.StatusVisitaService;
import com.jacto.agendamento.service.TipoServicoService;
import com.jacto.agendamento.service.UsuarioService;
import com.jacto.agendamento.service.VisitaTecnicaService;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @Autowired private FazendaService fazendaService; 
    @Autowired private VisitaTecnicaService visitaTecnicaService; 
    @Autowired private HistoricoAgendamentoVisitaTecnicaService historicoAgendamentoVisitaTecnicaService;
    @Autowired private EquipamentosVisitaTecnicaService equipamentosVisitaTecnicaService; 
    @Autowired private PecasReposicaoVisitaTecnicaService pecasReposicaoVisitaTecnicaService;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        try {
        logger.info("Iniciando carga de dados iniciais...");
           
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
   
         // Fazendas
        salvarFazendaSeNaoExistir("Fazenda Paraiso", 101011L, "", -2.511396, -44.246801, new Date(), true);
        salvarFazendaSeNaoExistir("Fazenda Descanso", 101011L, "", -2.493462, -44.267920, new Date(), true);          

        salvarUsuarioSeNaoExistir(
             "073.990.740-97", passwordEncoder.encode("123456"),
             2025002L, null,
             100,
             new Date(), true);

        salvarUsuarioSeNaoExistir(
             "098.961.300-35", passwordEncoder.encode("123456"), null,
             101011L, 200,
             new Date(), true);

        salvarVisitaTecnica(
            2025002L, 
            "Fazenda Paraiso", 
            new Date(), 
            15, 
            30, 
            100, // código de tipo de serviço
            100, // código de prioridade
            100, // código de status de visita
            null, // código de ocorrência
            true, // flag reagendamento
            true, // ativo
            100, // código do equipamento
            1,    // quantidade do equipamento
            100, // código da peça
            1    // quantidade da peça
        );

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        logger.info("now" + now);        
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 1); // Próxima hora
        Date nextHour = calendar.getTime();
        logger.info("nextHour" + nextHour);

        List<VisitaTecnica> visitasProximas = visitaTecnicaService.buscarPorDataHoraVisitaInicioAgendadoEntre(now, nextHour);
 
        logger.info("visitasProximas: " + visitasProximas.size());

        logger.info("Carga de dados inicial completa!");
        } catch (Exception e) {
            logger.error("Erro durante a carga de dados inicial: {}", e.getMessage(), e);
        }
    }

    private void salvarVisitaTecnica(
        Long matriculaFuncionario, String descricaoFazenda, Date dataHoraAgendamento,
        int minutosInicio, int minutosFim, Integer codigoTipoServico, Integer codigoPrioridade,
        Integer codigoStatusVisita, Integer codigoOcorrencia, boolean flagReagendamento,
        boolean ativo,
        Integer codigoEquipamento, Integer qtdeEquipamentos,
        Integer codigoPecaReposicao, Integer qtdePecasReposicao
    ) {

        Optional<Funcionario> funcionarioOptional = funcionarioService.buscarPorMatricula(matriculaFuncionario);
        Optional<Fazenda> fazendaOptional = fazendaService.findByDescricaoAndClienteMatricula(descricaoFazenda, 101011L);  // Assuming the matriculaCliente is fixed to 101011L (as in your original DataLoader)

        Optional<TipoServico> tipoServicoOptional = tipoServicoService.buscarPorCodigo(codigoTipoServico);
        Optional<Prioridade> prioridadeOptional = prioridadeService.buscarPorCodigo(codigoPrioridade);
        Optional<StatusVisita> statusVisitaOptional = statusVisitaService.buscarPorCodigo(codigoStatusVisita);

        Ocorrencia ocorrencia = null;
        if (codigoOcorrencia != null) {
            Optional<Ocorrencia> ocorrenciaOptional = ocorrenciaService.buscarPorCodigo(codigoOcorrencia);
            if (ocorrenciaOptional.isPresent()) {
                ocorrencia = ocorrenciaOptional.get();
            } else {
                logger.warn("Ocorrencia com código {} não encontrada.  VisitaTecnica será criada sem ocorrência.", codigoOcorrencia);
            }
        }

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(dataHoraAgendamento);
        calInicio.add(Calendar.MINUTE, minutosInicio);
        Date dataHoraVisitaInicioAgendado = calInicio.getTime();

        Calendar calFim = Calendar.getInstance();
        calFim.setTime(dataHoraAgendamento);
        calFim.add(Calendar.MINUTE, minutosFim);
        Date dataHoraVisitaFimAgendado = calFim.getTime();

        if (funcionarioOptional.isPresent() && fazendaOptional.isPresent() && tipoServicoOptional.isPresent()
                && prioridadeOptional.isPresent() && statusVisitaOptional.isPresent()) {

            VisitaTecnica visitaTecnica = new VisitaTecnica();
            visitaTecnica.setFuncionario(funcionarioOptional.get());
            visitaTecnica.setFazenda(fazendaOptional.get());
            visitaTecnica.setDataHoraAgendamento(dataHoraAgendamento);
            visitaTecnica.setDataHoraVisitaInicioAgendado(dataHoraVisitaInicioAgendado);
            visitaTecnica.setDataHoraVisitaFimAgendado(dataHoraVisitaFimAgendado);
            visitaTecnica.setTipoServico(tipoServicoOptional.get());
            visitaTecnica.setPrioridade(prioridadeOptional.get());
            visitaTecnica.setStatusVisita(statusVisitaOptional.get());
            visitaTecnica.setObservacao("Agendamento inicial");
            visitaTecnica.setOcorrencia(ocorrencia);
            visitaTecnica.setFlagReagendamento(flagReagendamento);

            visitaTecnicaService.salvar(visitaTecnica);

            // Salvar equipamentos da visita
            if (codigoEquipamento != null && qtdeEquipamentos != null) {
                salvarEquipamentosVisitaTecnica(visitaTecnica, codigoEquipamento, qtdeEquipamentos);
            }

            // Salvar peças de reposição da visita
            if (codigoPecaReposicao != null && qtdePecasReposicao != null) {
                salvarPecasReposicaoVisitaTecnica(visitaTecnica, codigoPecaReposicao, qtdePecasReposicao);
            }

            salvarHistoricoAgendamento(visitaTecnica, 100L, "073.990.740-97", new Date(), "Registro inicial da visita.");

            logger.info("Visita Técnica salva para Funcionário {}, Fazenda {}", matriculaFuncionario, descricaoFazenda);

        } else {
            logger.warn("Não foi possível salvar VisitaTecnica:  Um ou mais dados não encontrados.");
        }
    }

    private void salvarEquipamentosVisitaTecnica(VisitaTecnica visita, int codigoEquipamento, int quantidade) {
        Optional<EstoqueEquipamento> estoqueOptional = estoqueEquipamentoService.buscarPorCodigoEquipamento(codigoEquipamento);
        if (estoqueOptional.isPresent()) {
            EstoqueEquipamento equipamentoEstoque = estoqueOptional.get();
            EquipamentosVisitaTecnica evt = new EquipamentosVisitaTecnica();
            evt.setVisitaTecnica(visita);
            evt.setEquipamento(equipamentoEstoque.getEquipamento());
            evt.setQtde(quantidade);
            equipamentosVisitaTecnicaService.salvar(evt);
            logger.info("EquipamentoVisiteTecnica salvo para visita ID {}", visita.getId());
        } else {
            logger.warn("Equipamento com código {} não encontrado!", codigoEquipamento);
        }
    }

    private void salvarPecasReposicaoVisitaTecnica(VisitaTecnica visita, int codigoPeca, int quantidade) {
        Optional<EstoquePecaReposicao> estoqueOptional = estoquePecaReposicaoService.buscarPorCodigoPecaReposicao(codigoPeca);
        if (estoqueOptional.isPresent()) {
            EstoquePecaReposicao pecaEstoque = estoqueOptional.get();
            PecasReposicaoVisitaTecnica prvt = new PecasReposicaoVisitaTecnica();
            prvt.setVisitaTecnica(visita);
            prvt.setPecaReposicao(pecaEstoque.getPecaReposicao());
            prvt.setQtde(quantidade);
            pecasReposicaoVisitaTecnicaService.salvar(prvt);
            logger.info("PecaReposicaoVisitaTecnica salvo para visita ID {}", visita.getId());
        } else {
            logger.warn("PeçaReposição com código {} não encontrada!", codigoPeca);
        }
    }


    private void salvarHistoricoAgendamento(VisitaTecnica visitaTecnica, Long codigoOperacao, String loginUsuario, Date dataHoraOperacao, String observacao) {
        Optional<Operacao> operacaoOptional = operacaoService.buscarPorCodigo(codigoOperacao.intValue()); 
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorLogin(loginUsuario); 
 
        if (operacaoOptional.isPresent() && usuarioOptional.isPresent()) {
            HistoricoAgendamentoVisitaTecnica historico = new HistoricoAgendamentoVisitaTecnica();
            historico.setVisitaTecnica(visitaTecnica);
            historico.setOperacao(operacaoOptional.get());
            historico.setUsuario(usuarioOptional.get());
            historico.setDataHoraOperacao(dataHoraOperacao);
            historico.setObservacao(observacao);

            historicoAgendamentoVisitaTecnicaService.salvar(historico);
            logger.info("HistoricoAgendamentoVisitaTecnica salvo para VisitaTecnica ID {}", visitaTecnica.getId());
        } 
    }

    private void salvarFazendaSeNaoExistir(String descricao, Long matriculaCliente, String endereco, Double latitude, Double longitude, Date dataHoraCadastro, Boolean ativo) {
        Optional<Cliente> clienteOptional = clienteService.buscarPorMatricula(matriculaCliente);

        if (clienteOptional.isPresent()) {
            Optional<Fazenda> fazendaExistente = fazendaService.findByDescricaoAndClienteMatricula(descricao, matriculaCliente);

            if (fazendaExistente.isEmpty()) { 
                Fazenda novaFazenda = new Fazenda();
                novaFazenda.setCliente(clienteOptional.get());
                novaFazenda.setDescricao(descricao);
                novaFazenda.setEndereco(endereco);
                novaFazenda.setLatitude(latitude);
                novaFazenda.setLongitude(longitude);
                novaFazenda.setDataHoraCadastro(dataHoraCadastro);
                novaFazenda.setAtivo(ativo);
                fazendaService.salvar(novaFazenda);
                logger.info("Fazenda salva: descrição={}, matriculaCliente={}", descricao, matriculaCliente);
            }
        } else {
            logger.warn("Não foi possível salvar a fazenda: Cliente com a matricula {} não encontrado", matriculaCliente);
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