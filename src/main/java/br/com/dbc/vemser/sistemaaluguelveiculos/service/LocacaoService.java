package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            Locacao locacaoAdicionada = locacaoRepository.create(converterDTOEmLocacao(converterEmLocacao(locacaoDTO)));
            Funcionario funcionario = funcionarioRepository.findById(locacaoAdicionada.getFuncionario().getIdFuncionario());
            emailService.sendEmail(locacaoAdicionada, "locacao-template.ftl", funcionario.getEmail());
            return converterEmDTO(locacaoAdicionada);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            Locacao locacaoDeletada = converterDTOEmLocacao(findById(id));
            Funcionario funcionario = funcionarioRepository.findById(locacaoDeletada.getFuncionario().getIdFuncionario());
            locacaoRepository.delete(id);

            emailService.sendEmail(locacaoDeletada, "locacao-template-delete.ftl", funcionario.getEmail());
            emailService.sendEmail(locacaoDeletada, "locacao-template-delete.ftl", funcionario.getEmail());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public LocacaoDTO findById(Integer idLocacao) throws RegraDeNegocioException {
        try {
            Locacao locacaoRecuperada = locacaoRepository.findById(idLocacao);

            if (locacaoRecuperada.getIdLocacao() != null) {
                return converterEmDTO(locacaoRecuperada);
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
            CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.findById(locacaoCreateDTO.getIdCartaoCredito());
            Locacao locacaoEntity = objectMapper.convertValue(this.findById(id), Locacao.class);

            locacaoEntity.setVeiculo(objectMapper.convertValue(veiculoDTO, Veiculo.class));
            locacaoEntity.setCliente(objectMapper.convertValue(clienteDTO, Cliente.class));
            locacaoEntity.setFuncionario(objectMapper.convertValue(funcionarioDTO, Funcionario.class));
            locacaoEntity.setCartaoCredito(objectMapper.convertValue(cartaoCreditoDTO, CartaoCredito.class));
            locacaoEntity.setValorLocacao(locacaoCreateDTO.getValorLocacao());
            locacaoEntity.setDataLocacao(locacaoCreateDTO.getDataLocacao());
            locacaoEntity.setDataDevolucao(locacaoCreateDTO.getDataDevolucao());

            Locacao locacaoAdicionada = this.locacaoRepository.update(locacaoEntity.getIdLocacao(), locacaoEntity);
            emailService.sendEmail(locacaoEntity, "locacaoCreateDTO-template-update.ftl", funcionarioDTO.getEmail());
            return converterEmDTO(locacaoAdicionada);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
    }

    public List<LocacaoDTO> list() throws RegraDeNegocioException {
        try {
            List<Locacao> listar = locacaoRepository.list();
            return listar.stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public LocacaoDTO converterEmLocacao(LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {

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
                throw new RegraDeNegocioException("Endereço não encontrado.");
            }
            CartaoCredito cartaoCredito = cartaoCreditoRepository.findById(locacaoCreateDTO.getIdCartaoCredito());
            if (cartaoCredito.getIdCartaoCredito() == null) {
                throw new RegraDeNegocioException("Cartão de crédito não encontrado.");
            }
            Locacao locacao = new Locacao(null,
                    locacaoCreateDTO.getDataLocacao(),
                    locacaoCreateDTO.getDataDevolucao(),
                    locacaoCreateDTO.getValorLocacao(),
                    cliente,
                    veiculo,
                    cartaoCredito,
                    funcionario);
            return converterEmDTO(locacao);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao instanciar locação.");
        }
    }

    public LocacaoDTO converterEmDTO(Locacao locacao) {
        return objectMapper.convertValue(locacao, LocacaoDTO.class);
    }

    public Locacao converterDTOEmLocacao(LocacaoDTO locacaodto) {
        return objectMapper.convertValue(locacaodto, Locacao.class);
    }

}

