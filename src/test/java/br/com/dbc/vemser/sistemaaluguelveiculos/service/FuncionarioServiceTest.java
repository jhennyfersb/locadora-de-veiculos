package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.factory.FuncionarioFactory;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FuncionarioServiceTest {
    @InjectMocks
    private FuncionarioService funcionarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private LogService logService;

    @Mock
    private CargoService cargoService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(funcionarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        String nome = "Michael Jackson";
        FuncionarioCreateDTO funcionarioCreateDTO = FuncionarioFactory.getFuncionarioCreateDTO();
        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        funcionarioEntity.setIdFuncionario(10);
        when(funcionarioRepository.save(any())).thenReturn(funcionarioEntity);
        when(cargoService.findByIdCargo(anyInt())).thenReturn(funcionarioEntity.getCargoEntity());

        FuncionarioDTO funcionarioDTO = funcionarioService.create(funcionarioCreateDTO);

        Assertions.assertNotNull(funcionarioDTO);
        Assertions.assertNotNull(funcionarioDTO.getIdFuncionario());
        Assertions.assertEquals(nome, funcionarioDTO.getNome());
        verify(logService, times(1)).salvarLog(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {

        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        List<FuncionarioEntity> list = new ArrayList<>();
        list.add(funcionarioEntity);

        when(funcionarioRepository.findAll()).thenReturn(list);
        List<FuncionarioDTO> funcionarioDTOList = funcionarioService.list();

        Assertions.assertNotNull(funcionarioDTOList);
        Assertions.assertTrue(funcionarioDTOList.size() > 0);
        Assertions.assertEquals(1, funcionarioDTOList.size());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        Integer busca = 12;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity.setIdFuncionario(busca);

        when(funcionarioRepository.findById(anyInt())).thenReturn(Optional.of(funcionarioEntity));
        FuncionarioDTO funcionarioDTO = funcionarioService.findDtoById(busca);

        Assertions.assertNotNull(funcionarioDTO);
        Assertions.assertEquals(12, funcionarioDTO.getIdFuncionario());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer busca = 10;
        when(funcionarioRepository.findById(anyInt())).thenReturn(Optional.empty());
        funcionarioService.findDtoById(busca);
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        Integer id = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity.setIdFuncionario(12);
        when(funcionarioRepository.findById(anyInt())).thenReturn(Optional.of(funcionarioEntity));
        funcionarioService.delete(id);

        verify(funcionarioRepository, times(1)).deleteById(any());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        Integer id = 12;
        String nome = "Raul Gil";
        FuncionarioCreateDTO funcionarioCreateDTO = FuncionarioFactory.getFuncionarioCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity.setNome(nome);
        funcionarioEntity.setIdFuncionario(id);
        when(funcionarioRepository.getById(anyInt())).thenReturn(funcionarioEntity);

        when(funcionarioRepository.save(any())).thenReturn(FuncionarioFactory.getFuncionarioEntity());
        FuncionarioDTO funcionarioDTO = funcionarioService.update(id, funcionarioCreateDTO);

        Assertions.assertNotNull(funcionarioDTO);
        Assertions.assertNotEquals(nome, funcionarioDTO.getNome());
        verify(logService, times(1)).salvarLog(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    public void deveTestarGetIdLoggedUserComSucesso() {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
    }

    @Test
    public void deveTestarFindByLoginComSucesso() throws RegraDeNegocioException {
        String busca = "11122233344";
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity.setCpf(busca);

        when(funcionarioRepository.findByCpf(any())).thenReturn(Optional.of(funcionarioEntity));
        Optional<FuncionarioEntity> funcionarioEntity1 = funcionarioService.findByLogin(busca);

        Assertions.assertNotNull(funcionarioEntity1);
        Assertions.assertEquals(busca, funcionarioEntity1.get().getCpf());
    }

    @Test
    public void deveTestarAtualizarSenhaFuncionarioComSucesso() {
        String cpf = "11122233344";
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        String senha = "99999";
        when(funcionarioRepository.findByCpf(any())).thenReturn(Optional.of(funcionarioEntity));

        FuncionarioEntity funcionarioEntity1 = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity1.setSenha(senha);
        when(funcionarioRepository.save(any())).thenReturn(funcionarioEntity1);

        funcionarioService.atualizarSenhaFuncionario(cpf, senha);

        Assertions.assertEquals(senha, funcionarioEntity1.getSenha());
        verify(logService, times(1)).salvarLog(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(1,
                null,
                Collections.emptyList());
    }
}


