package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Endereco;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    public EnderecoDTO create(EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        try {
            Endereco enderecoAdicionado = enderecoRepository.create(converterEntity(enderecoCreateDTO));
            return converterEmDTO(enderecoAdicionado);
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        try {
            enderecoRepository.delete(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        try {
            return objectMapper.convertValue(enderecoRepository.update(id, converterEntity(enderecoCreateDTO)), EnderecoDTO.class);
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
    }

    public List<EnderecoDTO> list() throws RegraDeNegocioException {
        try {
            List<Endereco> listar = enderecoRepository.list();
        return listar.stream()
            .map(this::converterEmDTO)
            .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public Endereco converterEntity(EnderecoCreateDTO enderecoCreateDTO){
        return objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
    }

    public EnderecoDTO converterEmDTO(Endereco endereco){
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

//    public void removerEnderecosOciosos() throws RegraDeNegocioException {
//        try {
//            enderecoRepository.listarEnderecoSemVinculo()
//                .stream()
//                .forEach(enderecos -> {
//                    delete(enderecos.getIdEndereco());
//                });
//        }
//        catch (BancoDeDadosException e){
//            throw new RegraDeNegocioException("Erro ao remover no banco de dados.");
//        }
//    }

    public EnderecoDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            return converterEmDTO(enderecoRepository.findById(id));
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");
        }
    }
}
