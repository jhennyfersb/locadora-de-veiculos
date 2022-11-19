package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCreditoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ClienteEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ContatoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.BandeiraCartao;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.CartaoCreditoRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartaoCreditoServiceTest {
    @InjectMocks
    private CartaoCreditoService cartaoCreditoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmailService emailService;
    @Mock
    private LogService logService;

    @Mock
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(cartaoCreditoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        CartaoCreditoCreateDTO cartaoCreditoCreateDTO = getCartaoCreditoCreateDTO();
        CartaoCreditoEntity cartaoCreditoEntity = getCartaoCreditoEntity();


        when(cartaoCreditoRepository.save(any())).thenReturn(cartaoCreditoEntity);

        // Ação (ACT)
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.create(cartaoCreditoCreateDTO);

        // Verificação (ASSERT)
        assertNotNull(cartaoCreditoDTO);
        assertNotNull(cartaoCreditoDTO.getIdCartaoCredito());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException{
        // Criar variaveis (SETUP)
        Integer id = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(cartaoCreditoRepository.findById(anyInt())).thenReturn(Optional.of(getCartaoCreditoEntity()));
        // Ação (ACT)
        cartaoCreditoService.delete(id);
        // Verificação (ASSERT)
        verify(cartaoCreditoRepository,times(1)).deleteById(anyInt());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException{
        // Criar variaveis (SETUP)
        Integer id = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(cartaoCreditoRepository.findById(anyInt())).thenReturn(Optional.of(getCartaoCreditoEntity()));
        when(cartaoCreditoRepository.save(any())).thenReturn(getCartaoCreditoEntity());
        // Ação (ACT)
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.update(id,getCartaoCreditoCreateDTO());
        // Verificação (ASSERT)
        assertNotNull(cartaoCreditoDTO);
        assertEquals("9595 9898 9492 8788",cartaoCreditoDTO.getNumero());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException{
        // Criar variaveis (SETUP)
        // pessoaRepository.findAll()
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        List<CartaoCreditoEntity> lista = new ArrayList<>();
        lista.add(getCartaoCreditoEntity());
        when(cartaoCreditoRepository.findAll()).thenReturn(lista);

        // Ação (ACT)
        List<CartaoCreditoDTO> list = cartaoCreditoService.list();

        // Verificação (ASSERT)
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(1, lista.size());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarFindComSucesso() throws RegraDeNegocioException{
        // Criar variaveis (SETUP)
        Integer id = 1;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(cartaoCreditoRepository.findById(anyInt())).thenReturn(Optional.of(getCartaoCreditoEntity()));

        // Ação (ACT)
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.findDtoById(id);

        // Verificação (ASSERT)
        assertNotNull(cartaoCreditoDTO);
        assertEquals(cartaoCreditoDTO.getIdCartaoCredito(),id);
        verify(logService, times(1)).salvarLog(any());
    }

    private static CartaoCreditoCreateDTO getCartaoCreditoCreateDTO(){
        return new CartaoCreditoCreateDTO("9595 9898 9492 8788", BandeiraCartao.MASTERCARD,"09/2030",60000.00);
    }

    private static CartaoCreditoEntity getCartaoCreditoEntity(){
        return new CartaoCreditoEntity(1,"9595 9898 9492 8788", BandeiraCartao.MASTERCARD,"09/2030",60000.00,Collections.emptySet());
    }

    private static UsernamePasswordAuthenticationToken getAuthentication(){
        return new UsernamePasswordAuthenticationToken("00000000000", null, Collections.emptyList());
    }
}
