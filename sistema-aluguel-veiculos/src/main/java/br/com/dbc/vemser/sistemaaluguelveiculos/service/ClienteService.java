package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Cliente;
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
    private final EnderecoService enderecoService;
    private final ContatoService contatoService;
    private final ObjectMapper objectMapper;

    public ClienteDTO create(ClienteCreateDTO cliente) {
        try {
            Cliente clienteAdicionado = clienteRepository.create(converterEmCliente(cliente));
            System.out.println("Cliente adicinado com sucesso! " + clienteAdicionado);
            enderecoService.removerEnderecosOciosos();
            contatoService.removerContatosOciosos();
            return converterEmDTO(clienteAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClienteDTO update(Integer id, ClienteCreateDTO cliente) throws BancoDeDadosException {
        try {
            return objectMapper.convertValue(clienteRepository.update(id, converterEmCliente(cliente)), ClienteDTO.class);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer id) {
        try {
            boolean conseguiuRemover = clienteRepository.delete(id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public List<ClienteDTO> list() throws BancoDeDadosException {
        List<Cliente> listar = clienteRepository.list();
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
    }

    public int retornarIdContato(int id){
        return clienteRepository.retornarIndiceContatoPorIdCliente(id);
    }

    public int retornarIdEndereco(int id){
        return clienteRepository.retornarIndiceEnderecoPorIdCliente(id);
    }

    public Cliente converterEmCliente(ClienteCreateDTO clienteCreateDTO){
        return objectMapper.convertValue(clienteCreateDTO, Cliente.class);
    }

    public ClienteDTO converterEmDTO(Cliente cliente){
        return objectMapper.convertValue(cliente, ClienteDTO.class);
    }
}
