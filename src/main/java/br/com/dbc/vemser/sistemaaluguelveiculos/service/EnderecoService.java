package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.EnderecoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.pessoaapi.dto.PageDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    private final ClienteService clienteService;

    public EnderecoDTO create(EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        try {
            EnderecoEntity enderecoEntityAdicionado = enderecoRepository.save(converterEntity(enderecoCreateDTO));
            return converterEmDTO(enderecoEntityAdicionado);
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            this.findById(id);
            enderecoRepository.deleteById(id);
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        try {
            this.findById(id);
            EnderecoEntity enderecoEntity = converterEntity(enderecoCreateDTO);
            enderecoEntity.setIdEndereco(id);
            return converterEmDTO(enderecoRepository.save(enderecoEntity));
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao atualizar no banco de dados.");
        }
    }

//    public List<EnderecoDTO> list() throws RegraDeNegocioException {
//        try {
//            List<EnderecoEntity> listar = enderecoRepository.findAll();
//            return listar.stream()
//                    .map(this::converterEmDTO)
//                    .collect(Collectors.toList());
//        } catch (PersistenceException e) {
//            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
//        }
//    }

    public PageDTO<EnderecoDTO> list(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        try {
            Sort ordenacao = Sort.by("idCliente");
            PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
            Page<EnderecoEntity> listar = enderecoRepository.findAll(pageRequest);
            List<EnderecoDTO> enderecoPagina = listar.stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
            return new PageDTO<>(listar.getTotalElements(),
                    listar.getTotalPages(),
                    pagina,
                    tamanho,
                    enderecoPagina);
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public EnderecoEntity converterEntity(EnderecoCreateDTO enderecoCreateDTO) {
        return objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);
    }

    public EnderecoDTO converterEmDTO(EnderecoEntity enderecoEntity) {
        return objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
    }

    public EnderecoDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            Optional<EnderecoEntity> enderecoEntityRecuperado = enderecoRepository.findById(id);

            if (enderecoEntityRecuperado == null) {
                throw new RegraDeNegocioException("Endereço não encontrado");
            }
            return objectMapper.convertValue(enderecoEntityRecuperado,EnderecoDTO.class);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao procurar no banco de dados.");
        }
    }

    public PageDTO<EnderecoDTO> findEnderecoByIdCliente(Integer idCliente, Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        try {
            Sort ordenacao = Sort.by("idCliente");
            PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
            Page<EnderecoEntity> enderecoEntityRecuperado = enderecoRepository.findByIdClienteLike(idCliente, pageRequest);
            if (enderecoEntityRecuperado.isEmpty()) {
                throw new RegraDeNegocioException("Endereço não encontrado");
            }
            List<EnderecoDTO> dto = enderecoEntityRecuperado.stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
            return new PageDTO<>(enderecoEntityRecuperado.getTotalElements(),
                    enderecoEntityRecuperado.getTotalPages(),
                    pagina,
                    tamanho,
                    dto);
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao procurar no banco de dados.");
        }
    }
}
