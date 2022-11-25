package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ContatoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.EnderecoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final ObjectMapper objectMapper;
    private final LogService logService;

    private final ClienteService clienteService;

    public ContatoDTO create(ContatoCreateDTO contato) throws RegraDeNegocioException {

        ContatoEntity contatoEntityAdicionado = contatoRepository.save(converterEntity(contato));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.CREATE, "CPF logado: " + cpf, EntityLog.CONTATO));
        return converterEmDTO(contatoEntityAdicionado);

    }

    public void delete(Integer id) throws RegraDeNegocioException {

        findById(id);
        contatoRepository.deleteById(id);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.DELETE, "CPF logado: " + cpf, EntityLog.CONTATO));

    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contato) throws RegraDeNegocioException {

        findById(id);
        ContatoEntity contatoEntity = converterEntity(contato);
        contatoEntity.setIdContato(contato.getIdCliente());
        contatoEntity.setClienteEntity(clienteService.findById(contato.getIdCliente()));
        contatoEntity.setIdContato(id);
        ContatoDTO contatoDTO = converterEmDTO(contatoRepository.save(contatoEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE, "CPF logado: " + cpf, EntityLog.CONTATO));
        return contatoDTO;

    }

    public List<ContatoDTO> list() throws RegraDeNegocioException {

        List<ContatoEntity> listar = contatoRepository.findAll();
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.CONTATO));
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

    }

    private ContatoEntity converterEntity(ContatoCreateDTO contatoCreateDTO) {
        return objectMapper.convertValue(contatoCreateDTO, ContatoEntity.class);
    }

    private ContatoDTO converterEmDTO(ContatoEntity contatoEntity) {
        return objectMapper.convertValue(contatoEntity, ContatoDTO.class);
    }


    public ContatoDTO findDtoById(Integer id) throws RegraDeNegocioException {

        ContatoEntity contatoEntityRecuperado = findById(id);

        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.CONTATO));

        return converterEmDTO(contatoEntityRecuperado);
    }

    private ContatoEntity findById(Integer id) throws RegraDeNegocioException {
        return contatoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado"));
    }

    public List<ContatoEntity> findContatoByIdCliente(Integer idCliente) throws RegraDeNegocioException {

        List<ContatoEntity> contatoEntityList = contatoRepository.findByIdClienteLike(idCliente);
        if (contatoEntityList.isEmpty()) {
            throw new RegraDeNegocioException("Contato não encontrado");
        }
        return contatoEntityList;
    }
}
