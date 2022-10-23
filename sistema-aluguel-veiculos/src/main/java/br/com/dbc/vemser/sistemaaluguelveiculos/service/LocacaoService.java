package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Locacao;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.LocacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository locacaoRepository;
    private final ObjectMapper objectMapper;

    public LocacaoDTO create(LocacaoCreateDTO locacao) throws BancoDeDadosException {
        Locacao locacaoAdicionada = locacaoRepository.create(converterEmLocacao(locacao));
        System.out.println("locação adicinado com sucesso! \n" + locacaoAdicionada);
        return converterEmDTO(locacaoAdicionada);
    }

    public void delete(Integer id) {
        try {
            boolean conseguiuRemover = locacaoRepository.delete(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public LocacaoDTO update(Integer id, LocacaoCreateDTO locacao) throws BancoDeDadosException {
            boolean conseguiuEditar = locacaoRepository.update(id, converterEmLocacao(locacao));
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);
            return null;

    }

    public List<LocacaoDTO> list() throws BancoDeDadosException {
        List<Locacao> listar = locacaoRepository.list();
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
    }

    public Locacao converterEmLocacao(LocacaoCreateDTO locacaoCreateDTO){
        return objectMapper.convertValue(locacaoCreateDTO, Locacao.class);
    }

    public LocacaoDTO converterEmDTO(Locacao locacao){
        return objectMapper.convertValue(locacao, LocacaoDTO.class);
    }
}
