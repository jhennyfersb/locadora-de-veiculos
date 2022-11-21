package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.LocacaoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.factory.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.*;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class LocacaoServiceTest {


    @Mock
    private LocacaoRepository locacaoRepository;
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private CartaoCreditoRepository cartaoCreditoRepository;
    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private FuncionarioService funcionarioService;
    @Mock
    private ClienteService clienteService;
    @Mock
    private VeiculoService veiculoService;
    @Mock
    private CartaoCreditoService cartaoCreditoService;
    @Mock
    private EmailService emailService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private RelatorioLocacaoRepository relatorioLocacaoRepository;
    @Mock
    private LogService logService;
    @InjectMocks
    private LocacaoService locacaoService;


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(locacaoService, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(clienteService, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(funcionarioService, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(veiculoService, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(cartaoCreditoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {

        LocacaoCreateDTO locacaoCreateDTO = LocacaoFactory.getLocacaoCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        when(funcionarioRepository
                .findByCpf(any())).thenReturn(Optional.of(FuncionarioFactory.getFuncionarioEntity()));
        when(clienteRepository.findById(any())).thenReturn(Optional.of(ClienteFactory.getClienteEntity()));
        when(veiculoRepository.findById(any())).thenReturn(Optional.of(VeiculoFactory.getVeiculoEntity()));
        when(cartaoCreditoRepository.findById(any())).thenReturn(Optional.of(CartaoCreditoFactory.getCartaoCreditoEntity()));
        when(locacaoRepository.save(any())).thenReturn(LocacaoFactory.getLocacaoEntity());

        LocacaoDTO locacaoDTO = locacaoService.create(locacaoCreateDTO);
        Assertions.assertNotNull(locacaoDTO);
        Assertions.assertNotNull(locacaoDTO.getIdLocacao());
        Assertions.assertEquals(2, locacaoDTO.getClienteEntity().getIdCliente());
        verify(emailService, times(1)).sendEmail(anyString(), anyString());
        verify(relatorioLocacaoRepository, times(1)).save(any());
        verify(logService, times(1)).salvarLog(any());
    }


    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        Integer id = 2;
        LocacaoEntity locacaoEntity = LocacaoFactory.getLocacaoEntity();
        locacaoEntity.setIdLocacao(id);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        when(locacaoRepository.findById(anyInt())).thenReturn(Optional.of(locacaoEntity));
        locacaoService.delete(id);
        verify(locacaoRepository, times(1)).deleteById(any());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        Integer busca = 3;
        LocacaoEntity locacaoEntity = LocacaoFactory.getLocacaoEntity();
        locacaoEntity.setIdLocacao(busca);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        when(locacaoRepository.findById(anyInt())).thenReturn(Optional.of(locacaoEntity));
        LocacaoDTO locacaoDTO = locacaoService.findDtoById(busca);

        Assertions.assertNotNull(locacaoDTO);
        Assertions.assertEquals(3,locacaoDTO.getIdLocacao());
    }
    @Test(expected = RegraDeNegocioException.class)
    public void findByIdComErro() throws RegraDeNegocioException {
        Integer busca = 1;
        when(locacaoRepository.findById(anyInt())).thenReturn(Optional.empty());
        locacaoService.findDtoById(busca);
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        Integer id = 10;
        String qtLog = "1";
        Integer idVeiculo = 2;
        Integer idCartao = 4;
        LocacaoCreateDTO locacaoCreateDTO = LocacaoFactory.getLocacaoCreateDTO();
        LocacaoEntity locacaoEntity = LocacaoFactory.getLocacaoEntity();
        locacaoEntity.setIdLocacao(id);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        when(funcionarioService.findByLogin(any()))
                .thenReturn(Optional.of(FuncionarioFactory.getFuncionarioEntity()));
        when(funcionarioService.getIdLoggedUser())
                .thenReturn(qtLog);
        when(clienteService.findDToById(any()))
                .thenReturn(ClienteFactory.getClienteDTO());
        when(veiculoService.findDtoById(any()))
                .thenReturn(VeiculoFactory.getVeiculoDTO());
        when(locacaoRepository.findById(any()))
                .thenReturn(Optional.of(LocacaoFactory.getLocacaoEntity()));
        when(cartaoCreditoService.findDtoById(any()))
                .thenReturn(CartaoCreditoFactory.getCartaoCreditoCreateDTO());
        when(locacaoRepository.save(any())).thenReturn(locacaoEntity);

        LocacaoDTO locacaoDTO = locacaoService.update(id, locacaoCreateDTO);

        Assertions.assertNotNull(locacaoDTO);
        Assertions.assertNotNull(locacaoDTO.getIdLocacao());
        Assertions.assertEquals(idVeiculo, locacaoDTO.getVeiculoEntity().getIdVeiculo());
        Assertions.assertEquals(id, locacaoDTO.getIdLocacao());
        Assertions.assertEquals(idCartao, locacaoDTO.getCartaoCreditoEntity().getIdCartaoCredito());

    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        Integer qtList = 1;
        List<LocacaoEntity> lista = new ArrayList<>();
        lista.add(LocacaoFactory.getLocacaoEntity());
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        when(locacaoRepository.findAll()).thenReturn(lista);
        List<LocacaoDTO> list = locacaoService.list();

        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.size() > 0);
        Assertions.assertEquals(qtList, lista.size());
    }

    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(1,
                null,
                Collections.emptyList());
    }


}
