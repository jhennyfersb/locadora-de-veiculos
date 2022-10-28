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

    public ClienteDTO create(ClienteCreateDTO cliente) {
        try {
            Cliente clienteEntity = objectMapper.convertValue(cliente, Cliente.class);
            ClienteDTO clienteDTO = objectMapper.convertValue(clienteRepository.create(clienteEntity), ClienteDTO.class);
            return clienteDTO;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        try {
            Cliente clienteRecuperado = clienteRepository.getPorId(idCliente);

            if(clienteRecuperado.getIdCliente() != null) {
                Cliente clienteEntity = objectMapper.convertValue(cliente, Cliente.class);
                ClienteDTO clienteDTO = objectMapper.convertValue(clienteRepository.update(idCliente, clienteEntity), ClienteDTO.class);
                return clienteDTO;
            }else {
                throw new RegraDeNegocioException("Cliente não encontrado!");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer idCliente) throws RegraDeNegocioException {
        try {
            Cliente clienteRecuperado = clienteRepository.getPorId(idCliente);

            if(clienteRecuperado.getIdCliente() != null) {
                clienteRepository.delete(idCliente);
            }else {
                throw new RegraDeNegocioException("Cliente não encontrado!");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public List<ClienteDTO> list() {
        try {
            return clienteRepository.list().stream()
                    .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }
}
