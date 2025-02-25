package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ClienteEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.factory.ClienteFactory;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ClienteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmailService emailService;
    @Mock
    private LogService logService;

    @Mock
    private ClienteRepository clienteRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(clienteService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        ClienteCreateDTO clienteCreateDTO = ClienteFactory.getClienteDTO();
        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();

        when(clienteRepository.save(any())).thenReturn(clienteEntity);
        ClienteDTO clienteDTO = clienteService.create(clienteCreateDTO);


        assertNotNull(clienteDTO);
        assertNotNull(clienteDTO.getIdCliente());
        assertEquals(2, clienteDTO.getIdCliente());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        Integer id = 2;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(clienteRepository.findById(anyInt()))
                .thenReturn(Optional.of(ClienteFactory.getClienteEntity()));
        clienteService.delete(id);
        verify(clienteRepository, times(1)).deleteById(anyInt());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(clienteRepository.findById(anyInt()))
                .thenReturn(Optional.of(ClienteFactory.getClienteEntity()));
        when(clienteRepository.save(any())).thenReturn(ClienteFactory.getClienteEntity());

        ClienteDTO clienteDTO = clienteService.update(id, ClienteFactory.getClienteDTO());

        assertNotNull(clienteDTO);
        assertEquals("05671239451", clienteDTO.getCpf());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        List<ClienteEntity> lista = new ArrayList<>();
        lista.add(ClienteFactory.getClienteEntity());
        when(clienteRepository.findAll()).thenReturn(lista);


        List<ClienteDTO> list = clienteService.list();


        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarFindComSucesso() throws RegraDeNegocioException {
        Integer id = 2;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(clienteRepository.findById(anyInt()))
                .thenReturn(Optional.of(ClienteFactory.getClienteEntity()));

        ClienteDTO contato = clienteService.findDToById(id);

        assertNotNull(contato);
        assertEquals(contato.getIdCliente(), id);
        verify(logService, times(1)).salvarLog(any());
    }


    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken("00000000000",
                null, Collections.emptyList());
    }

}
