package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository locacaoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;
    private final CartaoCreditoRepository cartaoCreditoRepository;
    private final VeiculoRepository veiculoRepository;
    private final RelatorioLocacaoRepository relatorioLocacaoRepository;
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final CartaoCreditoService cartaoCreditoService;
    private final ProdutorService produtorService;
    private final CupomService cupomService;

    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final LogService logService;

    public LocacaoDTO create(LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException, JsonProcessingException {
        LocacaoEntity locacaoEntity = criarLocacaoAPartirDeIds(locacaoCreateDTO);
        ContatoEntity contato = locacaoEntity.getClienteEntity().getContatoEntities().stream().toList().get(0);

        if (!locacaoCreateDTO.getCupom().trim().isEmpty()) {
            CupomDTO cupomDTO = cupomService.findCupom(locacaoCreateDTO.getCupom());
            locacaoEntity.setValorLocacao(locacaoEntity.getValorLocacao()*(1-(cupomDTO.getDesconto()/100)));
            cupomDTO.setAtivo(false);
            cupomService.save(cupomDTO);
        }
        produtorService.enviarMensagem(contato.getEmail());
        LocacaoEntity locacaoSave = locacaoRepository.save(locacaoEntity);

        String base = "Olá, Você acaba de realizar uma locação.<br>" +
                "Dados da locacação:<br>" +
                "O identicador da locação é " + locacaoSave.getIdLocacao() + ".<br>" +
                "Valor da Locação: " + locacaoSave.getValorLocacao() + ".<br>" +
                "O veiculo é um " + locacaoSave.getVeiculoEntity().getModelo() +
                " da marca " + locacaoSave.getVeiculoEntity().getMarca() +
                " de placa  " + locacaoSave.getVeiculoEntity().getPlaca() + "<br>" +
                "Data da locação é " + locacaoSave.getDataLocacao() + ".<br>" +
                "Data de devolução " + locacaoSave.getDataDevolucao() + "<br>";

        emailService.sendEmail(base, locacaoSave.getFuncionarioEntity().getEmail());

        RelatorioLocacaoDTO relatorioLocacaoDTO = new RelatorioLocacaoDTO();

        relatorioLocacaoDTO.setNomeCliente(locacaoSave.getClienteEntity().getNome());
        relatorioLocacaoDTO.setCpfCliente(locacaoSave.getClienteEntity().getCpf());
        relatorioLocacaoDTO.setEmail(locacaoSave.getFuncionarioEntity().getEmail());
        relatorioLocacaoDTO.setDataLocacao(locacaoSave.getDataLocacao());
        relatorioLocacaoDTO.setDataDevolucao(locacaoSave.getDataDevolucao());
        relatorioLocacaoDTO.setValor(locacaoSave.getValorLocacao());
        relatorioLocacaoDTO.setMarca(locacaoSave.getVeiculoEntity().getMarca());
        relatorioLocacaoDTO.setModelo(locacaoSave.getVeiculoEntity().getModelo());
        relatorioLocacaoDTO.setCor(locacaoSave.getVeiculoEntity().getCor());
        relatorioLocacaoDTO.setAno(locacaoSave.getVeiculoEntity().getAno());
        relatorioLocacaoDTO.setPlaca(locacaoSave.getVeiculoEntity().getPlaca());
        relatorioLocacaoDTO.setQuilometragem(locacaoSave.getVeiculoEntity().getQuilometragem());
        relatorioLocacaoDTO.setNomeFuncionario(locacaoSave.getFuncionarioEntity().getNome());

        relatorioLocacaoRepository.save(relatorioLocacaoDTO);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.CREATE, "CPF logado: " + cpf, EntityLog.LOCACAO));
        return converterEmDTO(locacaoSave);
    }

    public void delete(Integer id) throws RegraDeNegocioException {

        LocacaoDTO locacaoEntityDeletada = converterEmDTO(findById(id));
        locacaoRepository.deleteById(id);
        locacaoEntityDeletada.getVeiculoEntity().setDisponibilidadeVeiculo(DisponibilidadeVeiculo.DISPONIVEL);
        veiculoRepository.save(objectMapper.convertValue(locacaoEntityDeletada.getVeiculoEntity(), VeiculoEntity.class));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.DELETE, "CPF logado: " + cpf, EntityLog.LOCACAO));
    }

    public LocacaoDTO findDtoById(Integer idLocacao) throws RegraDeNegocioException {

        LocacaoEntity locacaoEntityRecuperada = findById(idLocacao);

        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.LOCACAO));

        return converterEmDTO(locacaoEntityRecuperada);

    }

    private LocacaoEntity findById(Integer id) throws RegraDeNegocioException {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Locação não encontrado"));
    }

    public LocacaoDTO update(Integer id, LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {

        FuncionarioDTO funcionarioDTO = objectMapper.convertValue(funcionarioService.findByLogin(funcionarioService
                .getIdLoggedUser()).get(), FuncionarioDTO.class);
        ClienteDTO clienteDTO = clienteService.findDToById(locacaoCreateDTO.getIdCliente());
        VeiculoDTO veiculoDTO = veiculoService.findDtoById(locacaoCreateDTO.getIdVeiculo());
        LocacaoEntity locacaoEntity = findById(id);
        if (veiculoDTO.getDisponibilidadeVeiculo().getDisponibilidade() == 1 &&
                locacaoEntity.getVeiculoEntity().getIdVeiculo() != locacaoCreateDTO.getIdVeiculo()) {
            throw new RegraDeNegocioException("Veiculo selecionado alugado.");
        }
        CartaoCreditoEntity cartaoCreditoEntity = objectMapper.convertValue(cartaoCreditoService
                .findDtoById(locacaoCreateDTO.getIdCartaoCredito()), CartaoCreditoEntity.class);

        locacaoEntity.setVeiculoEntity(objectMapper.convertValue(veiculoDTO, VeiculoEntity.class));
        locacaoEntity.setClienteEntity(objectMapper.convertValue(clienteDTO, ClienteEntity.class));
        locacaoEntity.setFuncionarioEntity(objectMapper.convertValue(funcionarioDTO, FuncionarioEntity.class));
        locacaoEntity.setCartaoCreditoEntity(cartaoCreditoEntity);

        Duration d2 = Duration.between(locacaoEntity.getDataLocacao().atStartOfDay(),
                locacaoEntity.getDataDevolucao().atStartOfDay());
        locacaoEntity.setValorLocacao(d2.toDays() * locacaoEntity.getVeiculoEntity().getValorLocacao());

        locacaoEntity.setDataLocacao(locacaoCreateDTO.getDataLocacao());
        locacaoEntity.setDataDevolucao(locacaoCreateDTO.getDataDevolucao());

        if (locacaoEntity.getDataDevolucao().isBefore(locacaoEntity.getDataLocacao())) {
            throw new RegraDeNegocioException("A data da devolução não pode ser inferior a data de locação. Tente novamente!");
        }
        locacaoEntity.setIdLocacao(id);
        LocacaoEntity locacaoEntityAdicionada = this.locacaoRepository.save(locacaoEntity);
        locacaoEntityAdicionada.getVeiculoEntity().alterarDisponibilidadeVeiculo();
        veiculoRepository.save(locacaoEntityAdicionada.getVeiculoEntity());
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE, "CPF logado: " + cpf, EntityLog.LOCACAO));
        return converterEmDTO(locacaoEntityAdicionada);

    }

    public List<LocacaoDTO> list() throws RegraDeNegocioException {
        try {
            List<LocacaoDTO> lista = locacaoRepository.findAll()
                    .stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
            String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.LOCACAO));
            return lista;
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public LocacaoEntity criarLocacaoAPartirDeIds(LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {

        try {
            Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository
                    .findByCpf(funcionarioService.getIdLoggedUser());
            if (funcionarioEntity.isEmpty()) {
                throw new RegraDeNegocioException("Funcionário não encontrado.");
            }
            Optional<ClienteEntity> clienteEntity = clienteRepository.findById(locacaoCreateDTO.getIdCliente());
            if (clienteEntity.isEmpty()) {
                throw new RegraDeNegocioException("Cliente não encontrado.");
            }
            Optional<VeiculoEntity> veiculo = veiculoRepository.findById(locacaoCreateDTO.getIdVeiculo());
            if (veiculo.isEmpty()) {
                throw new RegraDeNegocioException("Veiculo não encontrado.");
            }
            if (veiculo.get().getDisponibilidadeVeiculo().getDisponibilidade() == 1) {
                throw new RegraDeNegocioException("Veiculo selecionado alugado.");
            }
            Optional<CartaoCreditoEntity> cartaoCreditoEntity = cartaoCreditoRepository
                    .findById(locacaoCreateDTO.getIdCartaoCredito());
            if (cartaoCreditoEntity.isEmpty()) {
                throw new RegraDeNegocioException("Cartão de crédito não encontrado.");
            }
            if (locacaoCreateDTO.getDataDevolucao().isBefore(locacaoCreateDTO.getDataLocacao())) {
                throw new RegraDeNegocioException("A data da devolução não pode ser inferior a data de locação. Tente novamente!");
            }


            LocacaoEntity locacaoEntity = new LocacaoEntity(null,
                    locacaoCreateDTO.getDataLocacao(),
                    locacaoCreateDTO.getDataDevolucao(),
                    clienteEntity.get(),
                    veiculo.get(),
                    cartaoCreditoEntity.get(),
                    funcionarioEntity.get());

            Duration d2 = Duration.between(locacaoEntity.getDataLocacao().atStartOfDay(),
                    locacaoEntity.getDataDevolucao().atStartOfDay());
            locacaoEntity.setValorLocacao(d2.toDays() * locacaoEntity.getVeiculoEntity().getValorLocacao());
            locacaoEntity.getVeiculoEntity().alterarDisponibilidadeVeiculo();


            return locacaoEntity;
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao instanciar locação.");
        }
    }

    public LocacaoDTO converterEmDTO(LocacaoEntity locacaoEntity) {
        ClienteDTO clienteDTO = objectMapper.convertValue(locacaoEntity.getClienteEntity(), ClienteDTO.class);
        VeiculoDTO veiculoDTO = objectMapper.convertValue(locacaoEntity.getVeiculoEntity(), VeiculoDTO.class);
        CartaoCreditoDTO cartaoCreditoDTO = objectMapper.convertValue(locacaoEntity
                .getCartaoCreditoEntity(), CartaoCreditoDTO.class);
        FuncionarioDTO funcionarioDTO = funcionarioService.converterEmDTO(locacaoEntity.getFuncionarioEntity());

        return new LocacaoDTO(locacaoEntity.getIdLocacao(),
                locacaoEntity.getDataLocacao(),
                locacaoEntity.getDataDevolucao(),
                locacaoEntity.getValorLocacao(),
                clienteDTO,
                veiculoDTO,
                cartaoCreditoDTO,
                funcionarioDTO);
    }

    public List<RelatorioLocacaoDTO> listarRelatoriosLocacao() {
        return relatorioLocacaoRepository.findAll();
    }

    public List<RelatorioLocacaoPorClienteDTO> locacaoPorClienteQuantidade() {
        return locacaoRepository.locacaoPorClienteQuantidade();
    }

    public List<RelatorioLocacaoPorCidadeDTO> locacaoPorCidade() {
        return locacaoRepository.locacaoPorCidade();
    }
}

