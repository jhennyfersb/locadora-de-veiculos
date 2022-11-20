package br.com.dbc.vemser.sistemaaluguelveiculos.service;


import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.PageDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

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
    public void init() {
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

    @Test
    public void deveTestarListPaginadoComSucesso() throws RegraDeNegocioException {
        Integer pagina = 10;
        Integer quantidade = 5;
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        Page<EnderecoEntity> paginaMock = new PageImpl<>(List.of(enderecoEntity));
        when(enderecoRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);

        PageDTO<EnderecoDTO> paginaSolicitada = enderecoService.list(pagina,quantidade);

        Assertions.assertNotNull(paginaSolicitada);
        Assertions.assertEquals(5, paginaSolicitada.getTamanho());
        Assertions.assertEquals(1, paginaSolicitada.getElementos().size());

    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id= 10;
        EnderecoCreateDTO enderecoCreateDTO = EnderecoFactory.getEnderecoCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        enderecoEntity.setCidade("Franca");
        enderecoEntity.setIdEndereco(id);

        when(enderecoRepository.save(any())).thenReturn(EnderecoFactory.getEnderecoEntity());

        // ACT
        EnderecoDTO enderecoDTO = enderecoService.update(id, enderecoCreateDTO);

        // ASSERT
        Assertions.assertNotNull(enderecoDTO);
        Assertions.assertNotEquals("Franca", enderecoDTO.getCidade());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        // Ação (ACT)
        enderecoService.delete(id);
        // Verificação (ASSERT)
        verify(enderecoRepository, times(1)).deleteById(anyInt());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarfindEnderecoByIdClienteComSucesso() throws RegraDeNegocioException {
        Integer pagina = 10;
        Integer quantidade = 5;
        Integer idCliente = 2;
        EnderecoEntity enderecoEntity = EnderecoFactory.getEnderecoEntity();
        enderecoEntity.setIdCliente(idCliente);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        Page<EnderecoEntity> paginaMock = new PageImpl<>(List.of(enderecoEntity));
        when(enderecoRepository.findByIdClienteLike(anyInt(),any(Pageable.class))).thenReturn(paginaMock);

        PageDTO<EnderecoDTO> paginaSolicitada = enderecoService.findEnderecoByIdCliente(idCliente,pagina,quantidade);

        Assertions.assertNotNull(paginaSolicitada);
        Assertions.assertEquals(5, paginaSolicitada.getTamanho());
        Assertions.assertEquals(1, paginaSolicitada.getElementos().size());
        Assertions.assertEquals(2, paginaSolicitada.getElementos().get(0).getIdCliente());
    }

    private static UsernamePasswordAuthenticationToken getAuthentication(){
        return new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
    }
}
