package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CargoEntity;
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
    @InjectMocks // classe principal de testes
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
        // Criar variaveis (SETUP)
        FuncionarioCreateDTO funcionarioCreateDTO = FuncionarioFactory.getFuncionarioCreateDTO();
        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        funcionarioEntity.setIdFuncionario(10);
        when(funcionarioRepository.save(any())).thenReturn(funcionarioEntity);
        when(cargoService.findByIdCargo(anyInt())).thenReturn(funcionarioEntity.getCargoEntity());

        // Ação (ACT)
        FuncionarioDTO funcionarioDTO = funcionarioService.create(funcionarioCreateDTO);

        // Verificação (ASSERT)
        Assertions.assertNotNull(funcionarioDTO);
        Assertions.assertNotNull(funcionarioDTO.getIdFuncionario());
        Assertions.assertEquals("Michael Jackson", funcionarioDTO.getNome());
        verify(logService, times(1)).salvarLog(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        List<FuncionarioEntity> list = new ArrayList<>();
        list.add(funcionarioEntity);

        when(funcionarioRepository.findAll()).thenReturn(list);

//        // ACT
        List<FuncionarioDTO> funcionarioDTOList = funcionarioService.list();

//        // ASSERT
        Assertions.assertNotNull(funcionarioDTOList);
        Assertions.assertTrue(funcionarioDTOList.size() > 0);
        Assertions.assertEquals(1, funcionarioDTOList.size());
        verify(logService, times(1)).salvarLog(any());
    }
//
    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 12;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity.setIdFuncionario(busca);

        when(funcionarioRepository.findById(anyInt())).thenReturn(Optional.of(funcionarioEntity));

        // Ação (ACT)
        FuncionarioDTO funcionarioDTO = funcionarioService.findById(busca, false);

        // Verificação (ASSERT)
        Assertions.assertNotNull(funcionarioDTO);
        Assertions.assertEquals(12, funcionarioDTO.getIdFuncionario());
    }
//
    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(funcionarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Ação (ACT)
        funcionarioService.findById(busca, false);
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        //pessoaRepository.findById(id)
        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity.setIdFuncionario(12);
        when(funcionarioRepository.findById(anyInt())).thenReturn(Optional.of(funcionarioEntity));

        // Ação (ACT)
        funcionarioService.delete(id);

        // Verificação (ASSERT)
        verify(funcionarioRepository, times(1)).deleteById(any());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id= 12;
        FuncionarioCreateDTO funcionarioCreateDTO = FuncionarioFactory.getFuncionarioCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity.setNome("Raul Gil");
        funcionarioEntity.setIdFuncionario(id);
        when(funcionarioRepository.getById(anyInt())).thenReturn(funcionarioEntity);

        FuncionarioEntity funcionarioEntity1 = FuncionarioFactory.getFuncionarioEntity();
        when(funcionarioRepository.save(any())).thenReturn(funcionarioEntity1);

        // ACT
        FuncionarioDTO funcionarioDTO = funcionarioService.update(id, funcionarioCreateDTO);

        // ASSERT
        Assertions.assertNotNull(funcionarioDTO);
        Assertions.assertNotEquals("Raul Gil", funcionarioDTO.getNome());
        verify(logService, times(1)).salvarLog(any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    public void deveTestarGetIdLoggedUserComSucesso(){
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
    }

    @Test
    public void deveTestarFindByLoginComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String busca = "11122233344";
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity.setCpf(busca);

        when(funcionarioRepository.findByCpf(any())).thenReturn(Optional.of(funcionarioEntity));

        // Ação (ACT)
        Optional<FuncionarioEntity> funcionarioEntity1 = funcionarioService.findByLogin(busca);

        // Verificação (ASSERT)
        Assertions.assertNotNull(funcionarioEntity1);
        Assertions.assertEquals("11122233344", funcionarioEntity1.get().getCpf());
    }

    @Test
    public void deveTestarAtualizarSenhaFuncionarioComSucesso() throws RegraDeNegocioException {
        // SETUP
        String cpf= "11122233344";
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        FuncionarioEntity funcionarioEntity = FuncionarioFactory.getFuncionarioEntity();
        String senha = "99999";
        when(funcionarioRepository.findByCpf(any())).thenReturn(Optional.of(funcionarioEntity));

        FuncionarioEntity funcionarioEntity1 = FuncionarioFactory.getFuncionarioEntity();
        funcionarioEntity1.setSenha(senha);
        when(funcionarioRepository.save(any())).thenReturn(funcionarioEntity1);

        // ACT
        funcionarioService.atualizarSenhaFuncionario(cpf, senha);

        // ASSERT
        Assertions.assertEquals("99999", funcionarioEntity1.getSenha());
        verify(logService, times(1)).salvarLog(any());
        verify(passwordEncoder, times(1)).encode(any());
    }



    private static UsernamePasswordAuthenticationToken getAuthentication(){
        return new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
    }
}


