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
            Locacao locacaoAdicionada = locacaoRepository.create(converterDTOEmLocacao(converterEmLocacao(locacaoDTO)));
            Funcionario funcionario = funcionarioRepository.findById(locacaoAdicionada.getFuncionario().getIdFuncionario());
            locacaoAdicionada.getVeiculo().alterarDisponibilidadeVeiculo();
            veiculoRepository.update(locacaoAdicionada.getVeiculo().getIdVeiculo(), locacaoAdicionada.getVeiculo());

            emailService.sendEmail(locacaoAdicionada, "locacao-template.ftl", funcionario.getEmail());
            return converterEmDTO(locacaoAdicionada);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            Locacao locacaoDeletada = converterDTOEmLocacao(findById(id));
            locacaoRepository.delete(id);
            locacaoDeletada.getVeiculo().alterarDisponibilidadeVeiculo();
            veiculoRepository.update(locacaoDeletada.getVeiculo().getIdVeiculo(), locacaoDeletada.getVeiculo());
            emailService.sendEmail(locacaoDeletada,
                    "locacao-template-delete.ftl",
                    locacaoDeletada.getFuncionario().getEmail());
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

            if(veiculoDTO.getDisponibilidadeVeiculo().getDisponibilidade() == 1){
                throw new RegraDeNegocioException("Veiculo selecionado alugado.");
            }
            CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.findById(locacaoCreateDTO.getIdCartaoCredito());
            Locacao locacaoEntity = objectMapper.convertValue(this.findById(id), Locacao.class);

            locacaoEntity.setVeiculo(objectMapper.convertValue(veiculoDTO, Veiculo.class));
            locacaoEntity.setCliente(objectMapper.convertValue(clienteDTO, Cliente.class));
            locacaoEntity.setFuncionario(objectMapper.convertValue(funcionarioDTO, Funcionario.class));
            locacaoEntity.setCartaoCredito(objectMapper.convertValue(cartaoCreditoDTO, CartaoCredito.class));

            Duration d2 = Duration.between(locacaoEntity.getDataLocacao().atStartOfDay(),
                    locacaoEntity.getDataDevolucao().atStartOfDay());
            locacaoEntity.setValorLocacao(d2.toDays() * locacaoEntity.getVeiculo().getValorLocacao());

            locacaoEntity.setDataLocacao(locacaoCreateDTO.getDataLocacao());
            locacaoEntity.setDataDevolucao(locacaoCreateDTO.getDataDevolucao());

            if(locacaoEntity.getDataDevolucao().isBefore(locacaoEntity.getDataLocacao())) {
                throw new RegraDeNegocioException("A data da devolução não pode ser inferior a data de locação. Tente novamente!");
            }

            Locacao locacaoAdicionada = this.locacaoRepository.update(locacaoEntity.getIdLocacao(), locacaoEntity);
            locacaoAdicionada.getVeiculo().alterarDisponibilidadeVeiculo();
            veiculoRepository.update(locacaoAdicionada.getVeiculo().getIdVeiculo(), locacaoAdicionada.getVeiculo());

            emailService.sendEmail(locacaoEntity, "locacao-template-update.ftl", funcionarioDTO.getEmail());
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
                throw new RegraDeNegocioException("Veiculo não encontrado.");
            }
            if(veiculo.getDisponibilidadeVeiculo().getDisponibilidade() == 1){
                throw new RegraDeNegocioException("Veiculo selecionado alugado.");
            }
            CartaoCredito cartaoCredito = cartaoCreditoRepository.findById(locacaoCreateDTO.getIdCartaoCredito());
            if (cartaoCredito.getIdCartaoCredito() == null) {
                throw new RegraDeNegocioException("Cartão de crédito não encontrado.");
            }
            if(locacaoCreateDTO.getDataDevolucao().isBefore(locacaoCreateDTO.getDataLocacao())) {
                throw new RegraDeNegocioException("A data da devolução não pode ser inferior a data de locação. Tente novamente!");
            }

            Locacao locacao = new Locacao(null,
                    locacaoCreateDTO.getDataLocacao(),
                    locacaoCreateDTO.getDataDevolucao(),
                    cliente,
                    veiculo,
                    cartaoCredito,
                    funcionario);
            Duration d2 = Duration.between(locacao.getDataLocacao().atStartOfDay(), locacao.getDataDevolucao().atStartOfDay());
            locacao.setValorLocacao(d2.toDays() * locacao.getVeiculo().getValorLocacao());

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

