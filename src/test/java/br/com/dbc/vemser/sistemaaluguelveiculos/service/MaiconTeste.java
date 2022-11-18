//package br.com.dbc.vemser.sistemaaluguelveiculos.service;
//
//import br.com.dbc.vemser.pessoaapi.dto.PessoaCreateDTO;
//import br.com.dbc.vemser.pessoaapi.dto.PessoaDTO;
//import br.com.dbc.vemser.pessoaapi.entity.PessoaEntity;
//import br.com.dbc.vemser.pessoaapi.exception.RegraDeNegocioException;
//import br.com.dbc.vemser.pessoaapi.repository.PessoaRepository;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class MaiconTeste {
//
//    @InjectMocks // classe principal de testes
//    private PessoaService pessoaService;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Mock
//    private EmailService emailService;
//
//    @Mock
//    private PessoaRepository pessoaRepository;
//
//    // colocar essas linhas abaixo para testar getIdLoggedUser()
//    //        UsernamePasswordAuthenticationToken dto
//    //                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
//    //        SecurityContextHolder.getContext().setAuthentication(dto);
//
//
//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(pessoaService, "objectMapper", objectMapper);
//    }
//
//    // deveTestarAlgumaCoisa
//    @Test
//    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
//
//        PessoaEntity pessoaEntity = getPessoaEntity();
//
//        // pessoaRepository.save(pessoaEntity);
//        pessoaEntity.setIdPessoa(10);
//        when(pessoaRepository.save(any())).thenReturn(pessoaEntity);
//
//        // Ação (ACT)
//        PessoaDTO pessoaDTORetorno = pessoaService.create(pessoaCreateDTO);
//
//        // Verificação (ASSERT)
////        assertTrue(pessoaDTO != null);
//        assertNotNull(pessoaDTORetorno);
//        assertNotNull(pessoaDTORetorno.getIdPessoa());
//        assertEquals("12345678910", pessoaDTORetorno.getCpf());
//    }
//
//    @Test
//    public void deveTestarListComSucesso(){
//        // Criar variaveis (SETUP)
//        // pessoaRepository.findAll()
//        List<PessoaEntity> lista = new ArrayList<>();
//        lista.add(getPessoaEntity());
//        when(pessoaRepository.findAll()).thenReturn(lista);
//
//        // Ação (ACT)
//        List<PessoaDTO> list = pessoaService.list();
//
//        // Verificação (ASSERT)
//        assertNotNull(list);
//        assertTrue(list.size() > 0);
//        assertEquals(1, lista.size());
//    }
//
//    @Test
//    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        Integer busca = 10;
//
//        //pessoaRepository.findById(id)
//        PessoaEntity pessoaEntity1 = getPessoaEntity();
//        pessoaEntity1.setIdPessoa(10);
//        when(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoaEntity1));
//
//        // Ação (ACT)
//        PessoaEntity pessoaEntity = pessoaService.findById(busca);
//
//        // Verificação (ASSERT)
//        assertNotNull(pessoaEntity);
//        assertEquals(10, pessoaEntity.getIdPessoa());
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        Integer busca = 10;
//        when(pessoaRepository.findById(anyInt())).thenReturn(Optional.empty());
//
//
//        // Ação (ACT)
//        pessoaService.findById(busca);
//    }
//
//    @Test
//    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
//        // Criar variaveis (SETUP)
//        Integer id = 10;
//
//        //pessoaRepository.findById(id)
//        PessoaEntity pessoaEntity1 = getPessoaEntity();
//        pessoaEntity1.setIdPessoa(10);
//        when(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoaEntity1));
//
//        // Ação (ACT)
//        pessoaService.delete(id);
//
//        // Verificação (ASSERT)
//        // verificar se chamou pelo menos 1 vez o metodo pessoaRepository.delete(pessoaEntityRecuperada);
//        verify(pessoaRepository, times(1)).delete(any());
//
//        // emailService.sendEmail(pessoaEntityRecuperada, base);
//        verify(emailService, times(1)).sendEmail(any(), any());
//    }
//
//    @Test
//    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
//        // SETUP
//        Integer id= 10;
//        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
//
//        // findById(id);
//        PessoaEntity pessoaEntity1 = getPessoaEntity();
//        pessoaEntity1.setNome("Fulano de tal");
//        pessoaEntity1.setIdPessoa(10);
//        when(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoaEntity1));
//
//        // pessoaRepository.save(pessoaEntityRecuperada);
//        PessoaEntity pessoaEntity = getPessoaEntity();
//        when(pessoaRepository.save(any())).thenReturn(pessoaEntity);
//
//        // ACT
//        PessoaDTO pessoaDTO = pessoaService.update(id, pessoaCreateDTO);
//
//        // ASSERT
//        assertNotNull(pessoaDTO);
//        assertNotEquals("Fulano de tal", pessoaDTO.getNome());
//    }
//
//    @Test
//    public void deveTestarListPaginadoComSucesso(){
//        // SETUP
//        Integer pagina = 10;
//        Integer quantidade = 5;
//
//        //pessoaRepository.findAll(pageable);
//        PessoaEntity pessoaEntity = getPessoaEntity();
//        Page<PessoaEntity> paginaMock = new PageImpl<>(List.of(pessoaEntity));
//        when(pessoaRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);
//
//        // ACT
//        Page<PessoaEntity> paginaSolicitada = pessoaService.listPaginado(pagina, quantidade);
//
//        // ASSERT
//        assertNotNull(paginaSolicitada);
//        assertEquals(1, paginaSolicitada.getSize());
//        assertEquals(1, paginaSolicitada.getTotalElements());
//    }
//
//    private static PessoaEntity getPessoaEntity() {
//        PessoaEntity pessoaEntity = new PessoaEntity();
//        pessoaEntity.setEmail("maicon@hotmail.com");
//        pessoaEntity.setNome("Maicon");
//        pessoaEntity.setCpf("12345678910");
//        pessoaEntity.setDataNascimento(LocalDate.of(1991, 9, 8));
//        return pessoaEntity;
//    }
//
//    private static PessoaCreateDTO getPessoaCreateDTO() {
//        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();
//        pessoaCreateDTO.setCpf("12345678910");
//        pessoaCreateDTO.setEmail("maicon@hotmail.com");
//        pessoaCreateDTO.setNome("Maicon");
//        pessoaCreateDTO.setDataNascimento(LocalDate.of(1991, 9, 8));
//        return pessoaCreateDTO;
//    }
//}
