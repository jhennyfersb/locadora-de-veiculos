package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final VeiculoService veiculoService;
    private final CartaoCreditoService cartaoCreditoService;

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public LocacaoDTO create(LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {
        LocacaoEntity locacaoEntity = criarLocacaoAPartirDeIds(locacaoCreateDTO);
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
        return converterEmDTO(locacaoSave);
    }

    public void delete(Integer id) throws RegraDeNegocioException {

        LocacaoDTO locacaoEntityDeletada = findById(id);
        locacaoRepository.deleteById(id);
        locacaoEntityDeletada.getVeiculoEntity().setDisponibilidadeVeiculo(DisponibilidadeVeiculo.DISPONIVEL);
        veiculoRepository.save(objectMapper.convertValue(locacaoEntityDeletada.getVeiculoEntity(), VeiculoEntity.class));

    }

    public LocacaoDTO findById(Integer idLocacao) throws RegraDeNegocioException {

        Optional<LocacaoEntity> locacaoEntityRecuperada = locacaoRepository.findById(idLocacao);

        if (locacaoEntityRecuperada.isPresent()) {
            return converterEmDTO(locacaoEntityRecuperada.get());
        } else {
            throw new RegraDeNegocioException("Locação não encontrada");
        }
    }

    public LocacaoDTO update(Integer id, LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {

        FuncionarioDTO funcionarioDTO = funcionarioService
                .converterEmDTO(funcionarioRepository.findByCpf(funcionarioService.getIdLoggedUser()).get());
        ClienteDTO clienteDTO = clienteService.findById(locacaoCreateDTO.getIdCliente());
        VeiculoDTO veiculoDTO = veiculoService.findById(locacaoCreateDTO.getIdVeiculo());

        LocacaoEntity locacaoEntity = objectMapper.convertValue(this.findById(id), LocacaoEntity.class);
        if (veiculoDTO.getDisponibilidadeVeiculo().getDisponibilidade() == 1 &&
                locacaoEntity.getVeiculoEntity().getIdVeiculo() != locacaoCreateDTO.getIdVeiculo()) {
            throw new RegraDeNegocioException("Veiculo selecionado alugado.");
        }
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.findById(locacaoCreateDTO.getIdCartaoCredito());

        locacaoEntity.setVeiculoEntity(objectMapper.convertValue(veiculoDTO, VeiculoEntity.class));
        locacaoEntity.setClienteEntity(objectMapper.convertValue(clienteDTO, ClienteEntity.class));
        locacaoEntity.setFuncionarioEntity(objectMapper.convertValue(funcionarioDTO, FuncionarioEntity.class));
        locacaoEntity.setCartaoCreditoEntity(objectMapper.convertValue(cartaoCreditoDTO, CartaoCreditoEntity.class));

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
        return converterEmDTO(locacaoEntityAdicionada);

    }

    public List<LocacaoDTO> list() throws RegraDeNegocioException {
        try {
            return locacaoRepository.findAll()
                    .stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public LocacaoEntity criarLocacaoAPartirDeIds(LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {

        try {
            Optional<FuncionarioEntity> funcionarioEntity = funcionarioRepository.findByCpf(funcionarioService.getIdLoggedUser());
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
            Optional<CartaoCreditoEntity> cartaoCreditoEntity = cartaoCreditoRepository.findById(locacaoCreateDTO.getIdCartaoCredito());
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
        ClienteDTO clienteDTO = clienteService.converterEmDTO(locacaoEntity.getClienteEntity());
        VeiculoDTO veiculoDTO = veiculoService.converterEmDTO(locacaoEntity.getVeiculoEntity());
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.converterEmDTO(locacaoEntity.getCartaoCreditoEntity());
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

    public List<RelatorioLocacaoDTO> listarRelatoriosLocacao(Integer idCliente,
                                                             Integer idVeiculo,
                                                             Integer idFuncionario) {
        return locacaoRepository.listarRelatoriosLocacao(idCliente, idVeiculo, idFuncionario);
    }

    public List<RelatorioLocacaoPorClienteDTO> locacaoPorClienteQuantidade() {
        return locacaoRepository.locacaoPorClienteQuantidade();
    }

    public List<RelatorioLocacaoPorCidadeDTO> locacaoPorCidade() {
        return locacaoRepository.locacaoPorCidade();
    }
}

