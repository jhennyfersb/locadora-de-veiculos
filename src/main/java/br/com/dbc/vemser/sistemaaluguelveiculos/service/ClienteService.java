package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ClienteEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final LogService logService;

    public ClienteDTO create(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = converterEntity(cliente);
        ClienteDTO clienteDTO = converterEmDTO(clienteRepository.save(clienteEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.CREATE, "CPF logado: " + cpf, EntityLog.CLIENTE));
        return clienteDTO;

    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        this.findById(idCliente);
        ClienteEntity clienteEntity = converterEntity(cliente);
        clienteEntity.setIdCliente(idCliente);
        ;
        ClienteDTO clienteDTO = converterEmDTO(clienteRepository.save(clienteEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE, "CPF logado: " + cpf, EntityLog.CLIENTE));
        return clienteDTO;
    }

    public void delete(Integer idCliente) throws RegraDeNegocioException {
        this.findById(idCliente);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.DELETE, "CPF logado: " + cpf, EntityLog.CLIENTE));
        clienteRepository.deleteById(idCliente);
    }

    public List<ClienteDTO> list() throws RegraDeNegocioException {
        List<ClienteDTO> clienteDTOList = clienteRepository.findAll().stream()
                .map(cliente -> objectMapper.convertValue(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.CLIENTE));
        return clienteDTOList;
    }

    private ClienteEntity converterEntity(ClienteCreateDTO clienteCreateDTO) {
        return objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
    }

    private ClienteDTO converterEmDTO(ClienteEntity clienteEntity) {
        return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
    }

    public ClienteDTO findDToById(Integer id) throws RegraDeNegocioException {

        ClienteEntity clienteEntityRecuperado = findById(id);

        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.CLIENTE));

        return objectMapper.convertValue(clienteEntityRecuperado, ClienteDTO.class);
    }

    public ClienteEntity findById(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado"));
    }

    public List<RelatorioClienteDTO> relatorioCliente(Integer idCliente) {
        List<RelatorioClienteDTO> relatorioClienteDTOList = clienteRepository.relatorioCliente(idCliente);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.CLIENTE));
        return relatorioClienteDTOList;
    }

}
