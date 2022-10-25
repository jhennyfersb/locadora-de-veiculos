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

    public ContatoDTO create(ContatoCreateDTO contato) throws BancoDeDadosException {
        //        try {
        Contato contatoAdicionado = contatoRepository.create(converterEmContato(contato));
        return converterEmDTO(contatoAdicionado);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    // remoção
    public void delete(Integer id) throws BancoDeDadosException {
//        try {
        contatoRepository.delete(id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    // atualização de um objeto
    public ContatoDTO update(Integer id, ContatoCreateDTO contato) throws BancoDeDadosException {
//        try {
        return objectMapper.convertValue(contatoRepository.update(id, converterEmContato(contato)), ContatoDTO.class);
//            System.out.println("funcionario editado? " + conseguiuEditar + "| com id=" + id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    // leitura
    public List<ContatoDTO> list() throws BancoDeDadosException {
//        try {
        List<Contato> listar = contatoRepository.list();
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public Contato converterEmContato(ContatoCreateDTO contatoCreateDTO){
        return objectMapper.convertValue(contatoCreateDTO, Contato.class);
    }

    public ContatoDTO converterEmDTO(Contato contato){
        return objectMapper.convertValue(contato, ContatoDTO.class);
    }

    public void removerContatosOciosos() throws BancoDeDadosException {
        contatoRepository.listarContatoSemVinculo()
                .stream()
                .forEach(contato -> {
                    try {
                        delete(contato.getIdContato());
                    } catch (BancoDeDadosException e) {
                        e.printStackTrace();
                    }
                });
    }
}
