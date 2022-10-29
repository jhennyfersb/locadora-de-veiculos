package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;

    public FuncionarioDTO create(FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {
        try {
            Funcionario funcionarioAdicionado = funcionarioRepository.create(converterEntity(funcionario));
            return converterEmDTO(funcionarioAdicionado);

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            funcionarioRepository.findById(id);

            funcionarioRepository.delete(id);

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public FuncionarioDTO update(Integer id, FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {
        try {
            funcionarioRepository.findById(id);

            Funcionario funcionarioEntity = converterEntity(funcionario);
            return converterEmDTO(funcionarioRepository.update(id, funcionarioEntity));

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
//        try {
//            return objectMapper.convertValue(funcionarioRepository.update(id, converterEntity(funcionario)), FuncionarioDTO.class);
//        }catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
//        }
    }

    public List<FuncionarioDTO> list() throws RegraDeNegocioException {
        try {
            List<Funcionario> listar = funcionarioRepository.list();
            return listar
                .stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public Funcionario converterEntity(FuncionarioCreateDTO funcionarioCreateDTO){
        return objectMapper.convertValue(funcionarioCreateDTO, Funcionario.class);
    }

    public FuncionarioDTO converterEmDTO(Funcionario funcionario){
        return objectMapper.convertValue(funcionario, FuncionarioDTO.class);
    }

     public FuncionarioDTO findById(Integer id) throws RegraDeNegocioException{
         try {
             Funcionario funcionarioRecuperado = funcionarioRepository.findById(id);

             if(funcionarioRecuperado.getIdFuncionario() != null) {
                 return converterEmDTO(funcionarioRecuperado);
             }else {
                 throw new RegraDeNegocioException("Funcionario não encontrado");
             }
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");
        }
     }
}

