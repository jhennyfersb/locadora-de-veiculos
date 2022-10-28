package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Contato;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
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

    public ContatoDTO create(ContatoCreateDTO contato) throws RegraDeNegocioException {
        try {
        Contato contatoAdicionado = contatoRepository.create(converterEntity(contato));
        return converterEmDTO(contatoAdicionado);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
        contatoRepository.delete(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contato) throws RegraDeNegocioException {
        try {
        return objectMapper.convertValue(contatoRepository.update(id, converterEntity(contato)), ContatoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
    }

    public List<ContatoDTO> list() throws RegraDeNegocioException {
        try {
        List<Contato> listar = contatoRepository.list();
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public Contato converterEntity(ContatoCreateDTO contatoCreateDTO){
        return objectMapper.convertValue(contatoCreateDTO, Contato.class);
    }

    public ContatoDTO converterEmDTO(Contato contato){
        return objectMapper.convertValue(contato, ContatoDTO.class);
    }

//    public void removerContatosOciosos() throws RegraDeNegocioException {
//        try{
//        contatoRepository.listarContatoSemVinculo()
//            .stream()
//            .forEach(contato -> delete(contato.getIdContato()));
//        }catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro ao remover no banco de dados.");
//        }
//    }

    public ContatoDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            return converterEmDTO(contatoRepository.findById(id));
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");
        }
    }
}
