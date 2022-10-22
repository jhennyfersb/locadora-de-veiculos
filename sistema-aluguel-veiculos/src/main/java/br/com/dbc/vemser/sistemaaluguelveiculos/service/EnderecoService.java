package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Endereco;
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

    public EnderecoDTO create(EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException {
        //        try {
        Endereco enderecoAdicionado = enderecoRepository.create(converterEmEndereco(enderecoCreateDTO));
        return converterEmDTO(enderecoAdicionado);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    // remoção
    public void delete(Integer id) throws BancoDeDadosException {
//        try {
        enderecoRepository.delete(id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    // atualização de um objeto
    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoCreateDTO) throws BancoDeDadosException {
//        try {
        boolean conseguiuEditar = enderecoRepository.update(id, converterEmEndereco(enderecoCreateDTO));
//            System.out.println("funcionario editado? " + conseguiuEditar + "| com id=" + id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    // leitura
    public List<EnderecoDTO> list() throws BancoDeDadosException {
//        try {
        List<Endereco> listar = enderecoRepository.list();
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public Endereco converterEmEndereco(EnderecoCreateDTO enderecoCreateDTO){
        return objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
    }

    public EnderecoDTO converterEmDTO(Endereco endereco){
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    public void removerEnderecosOciosos() throws BancoDeDadosException {
//        try {
            enderecoRepository.listarEnderecoSemVinculo()
                    .stream()
                    .forEach(enderecos -> {
                        try {
                            delete(enderecos.getIdEndereco());
                        } catch (BancoDeDadosException e) {
                            e.printStackTrace();
                        }
                    });
//        }
//        catch (BancoDeDadosException e){
//            e.printStackTrace();
//        }
    }
}
