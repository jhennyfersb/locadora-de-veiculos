package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;

    public FuncionarioDTO create(FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {
        try {
            FuncionarioEntity funcionarioEntityAdicionado = funcionarioRepository.save(converterEntity(funcionario));
            return converterEmDTO(funcionarioEntityAdicionado);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            this.findById(id);

            funcionarioRepository.deleteById(id);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public FuncionarioDTO update(Integer id, FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {
        try {
            this.findById(id);

            FuncionarioEntity funcionarioEntity = converterEntity(funcionario);
            funcionarioEntity.setIdFuncionario(id);
            return converterEmDTO(funcionarioRepository.save(funcionarioEntity));

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
    }

    public List<FuncionarioDTO> list() throws RegraDeNegocioException {
        try {
            List<FuncionarioEntity> listar = funcionarioRepository.findAll();
            return listar
                    .stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public FuncionarioEntity converterEntity(FuncionarioCreateDTO funcionarioCreateDTO) {
        return objectMapper.convertValue(funcionarioCreateDTO, FuncionarioEntity.class);
    }

    public FuncionarioDTO converterEmDTO(FuncionarioEntity funcionarioEntity) {
        return objectMapper.convertValue(funcionarioEntity, FuncionarioDTO.class);
    }

    public FuncionarioDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            Optional<FuncionarioEntity> funcionarioEntityRecuperado = funcionarioRepository.findById(id);

            if (funcionarioEntityRecuperado == null) {
                throw new RegraDeNegocioException("Funcionario não encontrado");
            }
            return objectMapper.convertValue(funcionarioEntityRecuperado,FuncionarioDTO.class);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");
        }
    }
}

