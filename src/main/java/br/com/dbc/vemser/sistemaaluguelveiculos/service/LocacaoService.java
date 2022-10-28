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

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public LocacaoDTO create(LocacaoCreateDTO locacaoDTO) throws RegraDeNegocioException {
        try {
            Locacao locacaoAdicionada = locacaoRepository.create(converterDTOEmLocacao(converterEmLocacao(locacaoDTO)));
            //Funcionario funcionario = funcionarioRepository.findById(locacaoAdicionada.getFuncionario().getIdFuncionario());
           // emailService.sendEmail(locacaoAdicionada, "locacao-template.ftl", funcionario.getEmail());
            return converterEmDTO(locacaoAdicionada);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            boolean conseguiuRemover = locacaoRepository.delete(id);
            Locacao locacaoDeletada = findById(id);
            Funcionario funcionario = funcionarioRepository.findById(locacaoDeletada.getFuncionario().getIdFuncionario());

            emailService.sendEmail(locacaoDeletada, "locacao-template-delete.ftl", funcionario.getEmail());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public Locacao findById(Integer idLocacao) throws RegraDeNegocioException {
        try {
            return locacaoRepository.findById(idLocacao);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");
        }
    }

    public LocacaoDTO update(Integer id, LocacaoCreateDTO locacao) throws RegraDeNegocioException {
        try {
            Funcionario funcionario = funcionarioRepository.findById(id);
            Locacao locacaoEntity = objectMapper.convertValue(locacao, Locacao.class);
            //emailService.sendEmail(locacaoEntity, "locacao-template-update.ftl", funcionario.getEmail());
            return converterEmDTO(locacaoRepository.update(id, locacaoEntity));
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

    public LocacaoDTO converterEmLocacao(LocacaoCreateDTO locacaoCreateDTO) {

        try {
            Funcionario funcionario = funcionarioRepository.findById(locacaoCreateDTO.getIdFuncionario());
            Cliente cliente = clienteRepository.findById(locacaoCreateDTO.getIdCliente());
            Veiculo veiculo = veiculoRepository.findById(locacaoCreateDTO.getIdVeiculo());
            CartaoCredito cartaoCredito = cartaoCreditoRepository.findById(locacaoCreateDTO.getIdCartaoCredito());
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
            throw new RuntimeException(e);
        }
    }
    public LocacaoDTO converterEmDTO(Locacao locacao) {
        return objectMapper.convertValue(locacao, LocacaoDTO.class);
    }

    public Locacao converterDTOEmLocacao(LocacaoDTO locacaodto) {
        return objectMapper.convertValue(locacaodto, Locacao.class);
    }

}

