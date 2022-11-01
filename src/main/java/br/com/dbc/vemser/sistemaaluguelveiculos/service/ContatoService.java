package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ContatoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final ObjectMapper objectMapper;

    public ContatoDTO create(ContatoCreateDTO contato) throws RegraDeNegocioException {
        try {
            ContatoEntity contatoEntityAdicionado = contatoRepository.save(converterEntity(contato));
            return converterEmDTO(contatoEntityAdicionado);
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            this.findById(id);
            contatoRepository.deleteById(id);
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contato) throws RegraDeNegocioException {
        try {
            this.findById(id);
            ContatoEntity contatoEntity = converterEntity(contato);
            contatoEntity.setIdContato(id);
            return converterEmDTO(contatoRepository.save(contatoEntity));
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao atualizar no banco de dados.");
        }
    }

    public List<ContatoDTO> list() throws RegraDeNegocioException {
        try {
            List<ContatoEntity> listar = contatoRepository.findAll();
            return listar.stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public ContatoEntity converterEntity(ContatoCreateDTO contatoCreateDTO) {
        return objectMapper.convertValue(contatoCreateDTO, ContatoEntity.class);
    }

    public ContatoDTO converterEmDTO(ContatoEntity contatoEntity) {
        return objectMapper.convertValue(contatoEntity, ContatoDTO.class);
    }


    public ContatoDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            Optional<ContatoEntity> contatoEntityRecuperado = contatoRepository.findById(id);

            if (contatoEntityRecuperado == null) {
                throw new RegraDeNegocioException("Contato não encontrado");
            }
            return objectMapper.convertValue(contatoEntityRecuperado,ContatoDTO.class);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao procurar no banco de dados.");
        }
    }
}
