package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ClienteEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;

    public ClienteDTO create(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            ClienteEntity clienteEntity = converterEntity(cliente);
            return converterEmDTO(clienteRepository.save(clienteEntity));

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            this.findById(idCliente);

            ClienteEntity clienteEntity = converterEntity(cliente);
            clienteEntity.setIdCliente(idCliente);
            return converterEmDTO(clienteRepository.save(clienteEntity));

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao atualizar no banco de dados.");
        }
    }

    public void delete(Integer idCliente) throws RegraDeNegocioException {
        try {
            this.findById(idCliente);

            clienteRepository.deleteById(idCliente);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");

        }
    }

    public List<ClienteDTO> list() throws RegraDeNegocioException {
        try {
            return clienteRepository.findAll().stream()
                    .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao recuperar os dados no banco de dados.");
        }
    }

    public ClienteEntity converterEntity(ClienteCreateDTO clienteCreateDTO) {
        return objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
    }

    public ClienteDTO converterEmDTO(ClienteEntity clienteEntity) {
        return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
    }

    public ClienteDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            Optional<ClienteEntity> clienteEntityRecuperado = clienteRepository.findById(id);

            if (clienteEntityRecuperado == null) {
                throw new RegraDeNegocioException("Cliente não encontrado");
            }
            return objectMapper.convertValue(clienteEntityRecuperado,ClienteDTO.class);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao procurar no banco de dados.");
        }
    }
}
