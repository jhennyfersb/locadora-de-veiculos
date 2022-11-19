package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ClienteEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ContatoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ContatoRepository;
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
import org.springframework.util.ReflectionUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContatoServiceTest {

    @InjectMocks // classe principal de testes
    private ContatoService contatoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmailService emailService;
    @Mock
    private LogService logService;

    @Mock
    private ContatoRepository contatoRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(contatoService, "objectMapper", objectMapper);
    }


    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        ContatoCreateDTO contatoCreateDTO = getContatoCreateDTO();
        ContatoEntity contatoEntity = getContatoEntity();


        when(contatoRepository.save(any())).thenReturn(contatoEntity);

        // Ação (ACT)
        ContatoDTO contatoDTO = contatoService.create(contatoCreateDTO);

        // Verificação (ASSERT)
        assertNotNull(contatoDTO);
        assertNotNull(contatoDTO.getIdContato());
        assertEquals(1, contatoDTO.getIdCliente());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(contatoRepository.findById(anyInt())).thenReturn(Optional.of(getContatoEntity()));
        // Ação (ACT)
        contatoService.delete(id);
        // Verificação (ASSERT)
        verify(contatoRepository, times(1)).deleteById(anyInt());
        verify(logService, times(1)).salvarLog(any());
    }


    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(contatoRepository.findById(anyInt())).thenReturn(Optional.of(getContatoEntity()));
        when(contatoRepository.save(any())).thenReturn(getContatoEntity());
        // Ação (ACT)
        ContatoDTO contato = contatoService.update(id, getContatoCreateDTO());
        // Verificação (ASSERT)
        assertNotNull(contato);
        assertEquals("99595-1313", contato.getTelefone());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        // pessoaRepository.findAll()
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        List<ContatoEntity> lista = new ArrayList<>();
        lista.add(getContatoEntity());
        when(contatoRepository.findAll()).thenReturn(lista);

        // Ação (ACT)
        List<ContatoDTO> list = contatoService.list();

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarFindComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 2;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(contatoRepository.findById(anyInt())).thenReturn(Optional.of(getContatoEntity()));

        // Ação (ACT)
        ContatoDTO contato = contatoService.findDtoById(id);

        // Verificação (ASSERT)
        assertNotNull(contato);
        assertEquals(contato.getIdContato(), id);
        verify(logService, times(1)).salvarLog(any());
    }

    private static ContatoCreateDTO getContatoCreateDTO() {
        return new ContatoCreateDTO(2, "99595-1313", "bruno.bardu@dbccompany.com.br");
    }

    private static ContatoEntity getContatoEntity() {
        return new ContatoEntity(2, 1, "99595-1313", "bruno.bardu@dbccompany.com.br", getClienteEntity());
    }

    private static ClienteEntity getClienteEntity() {
        return new ClienteEntity(1, "Lucas", "00000000000", Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
    }

    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken("00000000000", null, Collections.emptyList());
    }


}
