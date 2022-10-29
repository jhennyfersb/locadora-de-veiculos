package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Cliente;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public ClienteDTO create(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            Cliente clienteEntity = converterEntity(cliente);
            return converterEmDTO(clienteRepository.create(clienteEntity));

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            clienteRepository.findById(idCliente);

            Cliente clienteEntity = converterEntity(cliente);
            return converterEmDTO(clienteRepository.update(idCliente, clienteEntity));

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao atualizar no banco de dados.");
        }
    }

    public void delete(Integer idCliente) throws RegraDeNegocioException {
        try {
            clienteRepository.findById(idCliente);

            clienteRepository.delete(idCliente);

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");

        }
    }

    public List<ClienteDTO> list() throws RegraDeNegocioException {
        try {
            return clienteRepository.list().stream()
                    .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                    .collect(Collectors.toList());

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao recuperar os dados no banco de dados.");
        }
    }

    public Cliente converterEntity(ClienteCreateDTO clienteCreateDTO){
        return objectMapper.convertValue(clienteCreateDTO, Cliente.class);
    }

    public ClienteDTO converterEmDTO(Cliente cliente){
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }

    public ClienteDTO findById(Integer id) throws RegraDeNegocioException{
        try {
            Cliente clienteRecuperado = clienteRepository.findById(id);

            if(clienteRecuperado.getIdCliente() != null) {
                return converterEmDTO(clienteRecuperado);
            }else {
                throw new RegraDeNegocioException("Cliente não encontrado");
            }
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao procurar no banco de dados.");
        }
    }
}
