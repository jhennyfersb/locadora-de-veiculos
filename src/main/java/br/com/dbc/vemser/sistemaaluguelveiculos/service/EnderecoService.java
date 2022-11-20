package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.PageDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.EnderecoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    private final LogService logService;
    private final ClienteService clienteService;


    public EnderecoDTO create(EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        clienteService.findById(enderecoCreateDTO.getIdCliente(), false);
        String cpfFuncionarioLogado = SecurityContextHolder
                .getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.CREATE, "CPF logado: " + cpfFuncionarioLogado, EntityLog.ENDERECO));
        EnderecoEntity enderecoEntityAdicionado = enderecoRepository.save(converterEntity(enderecoCreateDTO));
        return converterEmDTO(enderecoEntityAdicionado);
    }

    public void delete(Integer id) throws RegraDeNegocioException {

        clienteService.findById(id, false);
        enderecoRepository.deleteById(id);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.DELETE, "CPF logado: " + cpf, EntityLog.ENDERECO));

    }

    public EnderecoDTO update(Integer id, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        clienteService.findById(id, false);
        EnderecoEntity enderecoEntity = converterEntity(enderecoCreateDTO);
        enderecoEntity.setIdEndereco(id);

        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE, "CPF logado: " + cpf, EntityLog.ENDERECO));
        return converterEmDTO(enderecoRepository.save(enderecoEntity));
    }


    public PageDTO<EnderecoDTO> list(Integer pagina, Integer tamanho) throws RegraDeNegocioException {

        Sort ordenacao = Sort.by("idCliente");
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<EnderecoEntity> listar = enderecoRepository.findAll(pageRequest);
        List<EnderecoDTO> enderecoPagina = listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.ENDERECO));

        return new PageDTO<>(listar.getTotalElements(),
                listar.getTotalPages(),
                pagina,
                tamanho,
                enderecoPagina);
    }

    private EnderecoEntity converterEntity(EnderecoCreateDTO enderecoCreateDTO) {
        return objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);
    }

    private EnderecoDTO converterEmDTO(EnderecoEntity enderecoEntity) {
        return objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
    }

    public EnderecoDTO findById(Integer id, boolean gerarLog) throws RegraDeNegocioException {
        Optional<EnderecoEntity> enderecoEntityRecuperado = enderecoRepository.findById(id);

        if (enderecoEntityRecuperado == null) {
            throw new RegraDeNegocioException("Endereço não encontrado");
        }
        if (gerarLog) {
            String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.ENDERECO));
        }
        return objectMapper.convertValue(enderecoEntityRecuperado, EnderecoDTO.class);

    }

    public PageDTO<EnderecoDTO> findEnderecoByIdCliente(Integer idCliente, Integer pagina, Integer tamanho) throws RegraDeNegocioException {

        Sort ordenacao = Sort.by("idCliente");
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<EnderecoEntity> enderecoEntityRecuperado = enderecoRepository.findByIdClienteLike(idCliente, pageRequest);
        if (enderecoEntityRecuperado.isEmpty()) {
            throw new RegraDeNegocioException("Endereço não encontrado");
        }

        List<EnderecoDTO> dto = enderecoEntityRecuperado.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        return new PageDTO<>(enderecoEntityRecuperado.getTotalElements(),
                enderecoEntityRecuperado.getTotalPages(),
                pagina,
                tamanho,
                dto);
    }
}
