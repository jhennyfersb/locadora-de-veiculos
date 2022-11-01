package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
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

    public LocacaoDTO create(LocacaoCreateDTO locacaoDTO) throws RegraDeNegocioException {
        try {
            LocacaoEntity locacaoEntityAdicionada = locacaoRepository.create(converterEmLocacao(locacaoDTO));
            Funcionario funcionario = funcionarioRepository.findById(locacaoEntityAdicionada.getFuncionario().getIdFuncionario());
            locacaoEntityAdicionada.getVeiculo().alterarDisponibilidadeVeiculo();
            veiculoRepository.update(locacaoEntityAdicionada.getVeiculo().getIdVeiculo(), locacaoEntityAdicionada.getVeiculo());

            emailService.sendEmail(locacaoEntityAdicionada, "locacao-template.ftl", funcionario.getEmail());
            return converterEmDTO(locacaoEntityAdicionada);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            LocacaoEntity locacaoEntityDeletada = converterDTOEmLocacao(findById(id));
            locacaoRepository.delete(id);
            locacaoEntityDeletada.getVeiculo().alterarDisponibilidadeVeiculo();
            veiculoRepository.update(locacaoEntityDeletada.getVeiculo().getIdVeiculo(), locacaoEntityDeletada.getVeiculo());
            emailService.sendEmail(locacaoEntityDeletada,
                    "locacao-template-delete.ftl",
                    locacaoEntityDeletada.getFuncionario().getEmail());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public LocacaoDTO findById(Integer idLocacao) throws RegraDeNegocioException {
        try {
            LocacaoEntity locacaoEntityRecuperada = locacaoRepository.findById(idLocacao);

            if (locacaoEntityRecuperada.getIdLocacao() != null) {
                return converterEmDTO(locacaoEntityRecuperada);
            } else {
                throw new RegraDeNegocioException("Locação não encontrada");
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");
        }
    }

    public LocacaoDTO update(Integer id, LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {
        try {
            FuncionarioDTO funcionarioDTO = funcionarioService.findById(locacaoCreateDTO.getIdFuncionario());
            ClienteDTO clienteDTO = clienteService.findById(locacaoCreateDTO.getIdCliente());

            VeiculoDTO veiculoDTO = veiculoService.findById(locacaoCreateDTO.getIdVeiculo());

            if(veiculoDTO.getDisponibilidadeVeiculo().getDisponibilidade() == 1){
                throw new RegraDeNegocioException("Veiculo selecionado alugado.");
            }
            CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.findById(locacaoCreateDTO.getIdCartaoCredito());
            LocacaoEntity locacaoEntity = objectMapper.convertValue(this.findById(id), LocacaoEntity.class);

            locacaoEntity.setVeiculo(objectMapper.convertValue(veiculoDTO, Veiculo.class));
            locacaoEntity.setCliente(objectMapper.convertValue(clienteDTO, Cliente.class));
            locacaoEntity.setFuncionario(objectMapper.convertValue(funcionarioDTO, Funcionario.class));
            locacaoEntity.setCartaoCreditoEntity(objectMapper.convertValue(cartaoCreditoDTO, CartaoCreditoEntity.class));

            Duration d2 = Duration.between(locacaoEntity.getDataLocacao().atStartOfDay(),
                    locacaoEntity.getDataDevolucao().atStartOfDay());
            locacaoEntity.setValorLocacao(d2.toDays() * locacaoEntity.getVeiculo().getValorLocacao());

            locacaoEntity.setDataLocacao(locacaoCreateDTO.getDataLocacao());
            locacaoEntity.setDataDevolucao(locacaoCreateDTO.getDataDevolucao());

            if(locacaoEntity.getDataDevolucao().isBefore(locacaoEntity.getDataLocacao())) {
                throw new RegraDeNegocioException("A data da devolução não pode ser inferior a data de locação. Tente novamente!");
            }

            LocacaoEntity locacaoEntityAdicionada = this.locacaoRepository.update(locacaoEntity.getIdLocacao(), locacaoEntity);
            locacaoEntityAdicionada.getVeiculo().alterarDisponibilidadeVeiculo();
            veiculoRepository.update(locacaoEntityAdicionada.getVeiculo().getIdVeiculo(), locacaoEntityAdicionada.getVeiculo());

            emailService.sendEmail(locacaoEntity, "locacao-template-update.ftl", funcionarioDTO.getEmail());
            return converterEmDTO(locacaoEntityAdicionada);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
    }

    public List<LocacaoDTO> list() throws RegraDeNegocioException {
        try {
            List<LocacaoEntity> listar = locacaoRepository.list();
            return listar.stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public LocacaoEntity converterEmLocacao(LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {

        try {
            Funcionario funcionario = funcionarioRepository.findById(locacaoCreateDTO.getIdFuncionario());
            if (funcionario.getIdFuncionario() == null) {
                throw new RegraDeNegocioException("Funcionário não encontrado.");
            }
            Cliente cliente = clienteRepository.findById(locacaoCreateDTO.getIdCliente());
            if (cliente.getIdCliente() == null) {
                throw new RegraDeNegocioException("Cliente não encontrado.");
            }
            Veiculo veiculo = veiculoRepository.findById(locacaoCreateDTO.getIdVeiculo());
            if (veiculo.getIdVeiculo() == null) {
                throw new RegraDeNegocioException("Veiculo não encontrado.");
            }
            if(veiculo.getDisponibilidadeVeiculo().getDisponibilidade() == 1){
                throw new RegraDeNegocioException("Veiculo selecionado alugado.");
            }
            CartaoCreditoEntity cartaoCreditoEntity = cartaoCreditoRepository.findById(locacaoCreateDTO.getIdCartaoCredito());
            if (cartaoCreditoEntity.getIdCartaoCredito() == null) {
                throw new RegraDeNegocioException("Cartão de crédito não encontrado.");
            }
            if(locacaoCreateDTO.getDataDevolucao().isBefore(locacaoCreateDTO.getDataLocacao())) {
                throw new RegraDeNegocioException("A data da devolução não pode ser inferior a data de locação. Tente novamente!");
            }

            LocacaoEntity locacaoEntity = new LocacaoEntity(null,
                    locacaoCreateDTO.getDataLocacao(),
                    locacaoCreateDTO.getDataDevolucao(),
                    cliente,
                   veiculo,
                    cartaoCreditoEntity,
                    funcionario);

            Duration d2 = Duration.between(locacaoEntity.getDataLocacao().atStartOfDay(), locacaoEntity.getDataDevolucao().atStartOfDay());
            locacaoEntity.setValorLocacao(d2.toDays() * locacaoEntity.getVeiculo().getValorLocacao());

            return locacaoEntity;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao instanciar locação.");
        }
    }

    public LocacaoDTO converterEmDTO(LocacaoEntity locacaoEntity) {
        ClienteDTO clienteDTO = clienteService.converterEmDTO(locacaoEntity.getCliente());
        VeiculoDTO veiculoDTO = veiculoService.converterEmDTO(locacaoEntity.getVeiculo());
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.converterEmDTO(locacaoEntity.getCartaoCreditoEntity());
        FuncionarioDTO funcionarioDTO = funcionarioService.converterEmDTO(locacaoEntity.getFuncionario());

        new LocacaoDTO(locacaoEntity.getIdLocacao(), locacaoEntity.getDataLocacao(), locacaoEntity.getDataDevolucao(), locacaoEntity.getValorLocacao(),clienteDTO,
                veiculoDTO,cartaoCreditoDTO,funcionarioDTO);
        return objectMapper.convertValue(locacaoEntity, LocacaoDTO.class);
    }

    public LocacaoEntity converterDTOEmLocacao(LocacaoDTO locacaodto) {
        return objectMapper.convertValue(locacaodto, LocacaoEntity.class);
    }

}

