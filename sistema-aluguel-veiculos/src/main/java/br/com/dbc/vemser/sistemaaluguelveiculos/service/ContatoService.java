package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Contato;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final ObjectMapper objectMapper;

    public ContatoDTO create(ContatoCreateDTO contato) {
        try {
        Contato contatoAdicionado = contatoRepository.create(converterEmContato(contato));
        return converterEmDTO(contatoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer id) {
        try {
        contatoRepository.delete(id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contato) {
        try {
        return objectMapper.convertValue(contatoRepository.update(id, converterEmContato(contato)), ContatoDTO.class);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ContatoDTO> list() {
        try {
        List<Contato> listar = contatoRepository.list();
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Contato converterEmContato(ContatoCreateDTO contatoCreateDTO){
        return objectMapper.convertValue(contatoCreateDTO, Contato.class);
    }

    public ContatoDTO converterEmDTO(Contato contato){
        return objectMapper.convertValue(contato, ContatoDTO.class);
    }

    public void removerContatosOciosos() {
        try{
        contatoRepository.listarContatoSemVinculo()
            .stream()
            .forEach(contato -> delete(contato.getIdContato()));
        }catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }
}
