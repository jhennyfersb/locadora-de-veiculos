package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;

    public FuncionarioDTO create(FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {

        FuncionarioEntity funcionarioEntityAdicionado = funcionarioRepository.save(converterEntity(funcionario));
        return converterEmDTO(funcionarioEntityAdicionado);


    }

    public void delete(Integer id) throws RegraDeNegocioException {

        this.findById(id);
        funcionarioRepository.deleteById(id);

    }

    public FuncionarioDTO update(Integer id, FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {

        this.findById(id);

        FuncionarioEntity funcionarioEntity = converterEntity(funcionario);
        funcionarioEntity.setIdFuncionario(id);
        return converterEmDTO(funcionarioRepository.save(funcionarioEntity));


    }

    public List<FuncionarioDTO> list() throws RegraDeNegocioException {

        List<FuncionarioEntity> listar = funcionarioRepository.findAll();
        return listar
                .stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

    }

    public FuncionarioEntity converterEntity(FuncionarioCreateDTO funcionarioCreateDTO) {
        return objectMapper.convertValue(funcionarioCreateDTO, FuncionarioEntity.class);
    }

    public FuncionarioDTO converterEmDTO(FuncionarioEntity funcionarioEntity) {
        return objectMapper.convertValue(funcionarioEntity, FuncionarioDTO.class);
    }

    public FuncionarioDTO findById(Integer id) throws RegraDeNegocioException {

        Optional<FuncionarioEntity> funcionarioEntityRecuperado = funcionarioRepository.findById(id);

        if (funcionarioEntityRecuperado == null) {
            throw new RegraDeNegocioException("Funcionario não encontrado");
        }
        return objectMapper.convertValue(funcionarioEntityRecuperado, FuncionarioDTO.class);


    }
}

