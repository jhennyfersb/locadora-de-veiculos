package br.com.dbc.vemser.sistemaaluguelveiculos.service;


import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.EnderecoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.factory.EnderecoFactory;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {
    @InjectMocks
    private EnderecoService enderecoService;
    @Mock
    private EnderecoRepository enderecoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private LogService logService;

    @Mock
    private ClienteService clienteService;
    UsernamePasswordAuthenticationToken dto
            = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());

    @Before
    public void init(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(enderecoService, "objectMapper", objectMapper);
    }
    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        EnderecoCreateDTO enderecoCreateDTO = EnderecoFactory.getEnderecoCreateDTO();
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        SecurityContextHolder.getContext().setAuthentication(dto);

        when(enderecoRepository.save(any())).thenReturn(enderecoEntity);
        EnderecoDTO enderecoDTORetorno = enderecoService.create(enderecoCreateDTO);

        Assertions.assertNotNull(enderecoDTORetorno);
        Assertions.assertNotNull(enderecoDTORetorno.getIdEndereco());
        verify(logService, times(1)).salvarLog(any());
    }

}
