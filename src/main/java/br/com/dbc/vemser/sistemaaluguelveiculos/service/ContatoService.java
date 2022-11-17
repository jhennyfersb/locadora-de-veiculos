package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ContatoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final ObjectMapper objectMapper;
    private final LogService logService;

    public ContatoDTO create(ContatoCreateDTO contato) throws RegraDeNegocioException {

        ContatoEntity contatoEntityAdicionado = contatoRepository.save(converterEntity(contato));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.CREATE,"CPF logado: " + cpf, EntityLog.CONTATO));
        return converterEmDTO(contatoEntityAdicionado);

    }

    public void delete(Integer id) throws RegraDeNegocioException {

        this.findById(id, false);
        contatoRepository.deleteById(id);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.DELETE,"CPF logado: " + cpf, EntityLog.CONTATO));

    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contato) throws RegraDeNegocioException {

        this.findById(id, false);
        ContatoEntity contatoEntity = converterEntity(contato);
        contatoEntity.setIdContato(id);
        ContatoDTO contatoDTO = converterEmDTO(contatoRepository.save(contatoEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE,"CPF logado: " + cpf, EntityLog.CONTATO));
        return contatoDTO;

    }

    public List<ContatoDTO> list() throws RegraDeNegocioException {

        List<ContatoEntity> listar = contatoRepository.findAll();
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ,"CPF logado: " + cpf, EntityLog.CONTATO));
        return listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

    }

    public ContatoEntity converterEntity(ContatoCreateDTO contatoCreateDTO) {
        return objectMapper.convertValue(contatoCreateDTO, ContatoEntity.class);
    }

    public ContatoDTO converterEmDTO(ContatoEntity contatoEntity) {
        return objectMapper.convertValue(contatoEntity, ContatoDTO.class);
    }


    public ContatoDTO findById(Integer id, boolean gerarLog) throws RegraDeNegocioException {

        Optional<ContatoEntity> contatoEntityRecuperado = contatoRepository.findById(id);

        if (contatoEntityRecuperado == null) {
            throw new RegraDeNegocioException("Contato não encontrado");
        }
        if(gerarLog){
            String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            logService.salvarLog(new LogCreateDTO(TipoLog.READ,"CPF logado: "+cpf, EntityLog.CONTATO));
        }
        return objectMapper.convertValue(contatoEntityRecuperado, ContatoDTO.class);


    }
}
