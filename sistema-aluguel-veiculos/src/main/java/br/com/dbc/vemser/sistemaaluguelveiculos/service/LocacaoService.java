package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Locacao;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.LocacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository locacaoRepository;
    private final FuncionarioService funcionarioService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public LocacaoDTO create(LocacaoCreateDTO locacao) throws BancoDeDadosException, RegraDeNegocioException {
        Locacao locacaoAdicionada = locacaoRepository.create(converterEmLocacao(locacao));
        System.out.println("locação adicinado com sucesso! \n" + locacaoAdicionada);
        Funcionario funcionario = funcionarioService.findById(locacaoAdicionada.getFuncionario().getIdFuncionario());
        emailService.sendEmail(locacaoAdicionada, "locacao-template.ftl", funcionario.getEmail());
        return converterEmDTO(locacaoAdicionada);
    }

    public void delete(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        try {
            boolean conseguiuRemover = locacaoRepository.delete(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        Locacao locacaoDeletada = findById(id);
        Funcionario funcionario = funcionarioService.findById(locacaoDeletada.getFuncionario().getIdFuncionario());
        emailService.sendEmail(locacaoDeletada, "locacao-template-delete.ftl", funcionario.getEmail());
    }

    public Locacao findById(Integer idLocacao) throws RegraDeNegocioException, BancoDeDadosException {
        Locacao locacaoRecuperada = locacaoRepository.list().stream()
                .filter(locacao -> locacao.getFuncionario().getIdFuncionario().equals(idLocacao))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Locação não encontrada"));

        return locacaoRecuperada;
    }
    public LocacaoDTO update(Integer id, LocacaoCreateDTO locacao) throws Exception {
        Funcionario funcionario = funcionarioService.findById(id);
        emailService.sendEmail(locacao, "locacao-template-update.ftl", funcionario.getEmail());
        return objectMapper.convertValue(locacaoRepository.update(id, converterEmLocacao(locacao)), LocacaoDTO.class);
    }

    public List<LocacaoDTO> list() throws BancoDeDadosException {
        List<Locacao> listar = locacaoRepository.list();
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
    }

    public Locacao converterEmLocacao(LocacaoCreateDTO locacaoCreateDTO) {
        return objectMapper.convertValue(locacaoCreateDTO, Locacao.class);
    }

    public LocacaoDTO converterEmDTO(Locacao locacao) {
        return objectMapper.convertValue(locacao, LocacaoDTO.class);
    }
}
