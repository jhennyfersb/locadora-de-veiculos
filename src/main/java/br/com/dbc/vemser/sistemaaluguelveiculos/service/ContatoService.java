package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ContatoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final ObjectMapper objectMapper;

    public ContatoDTO create(ContatoCreateDTO contato) throws RegraDeNegocioException {

        ContatoEntity contatoEntityAdicionado = contatoRepository.save(converterEntity(contato));
        return converterEmDTO(contatoEntityAdicionado);

    }

    public void delete(Integer id) throws RegraDeNegocioException {

        this.findById(id);
        contatoRepository.deleteById(id);

    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contato) throws RegraDeNegocioException {

        this.findById(id);
        ContatoEntity contatoEntity = converterEntity(contato);
        contatoEntity.setIdContato(id);
        return converterEmDTO(contatoRepository.save(contatoEntity));

    }

    public List<ContatoDTO> list() throws RegraDeNegocioException {

        List<ContatoEntity> listar = contatoRepository.findAll();
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

    }

    public ContatoEntity converterEntity(ContatoCreateDTO contatoCreateDTO) {
        return objectMapper.convertValue(contatoCreateDTO, ContatoEntity.class);
    }

    public ContatoDTO converterEmDTO(ContatoEntity contatoEntity) {
        return objectMapper.convertValue(contatoEntity, ContatoDTO.class);
    }


    public ContatoDTO findById(Integer id) throws RegraDeNegocioException {

        Optional<ContatoEntity> contatoEntityRecuperado = contatoRepository.findById(id);

        if (contatoEntityRecuperado == null) {
            throw new RegraDeNegocioException("Contato não encontrado");
        }
        return objectMapper.convertValue(contatoEntityRecuperado, ContatoDTO.class);


    }
}
