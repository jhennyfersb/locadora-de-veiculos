package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.VeiculoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.VeiculoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class VeiculoServiceTest {
    @InjectMocks // classe principal de testes
    private VeiculoService veiculoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmailService emailService;

    @Mock
    private VeiculoRepository veiculoRepository;

    // colocar essas linhas abaixo para testar getIdLoggedUser()
    //        UsernamePasswordAuthenticationToken dto
    //                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
    //        SecurityContextHolder.getContext().setAuthentication(dto);


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(veiculoService, "objectMapper", objectMapper);
    }

    // deveTestarAlgumaCoisa
    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        VeiculoCreateDTO veiculoCreateDTO = getVeiculoCreateDTO();

        VeiculoEntity veiculoEntity = getVeiculoEntity();

        // pessoaRepository.save(pessoaEntity);
        veiculoEntity.setIdVeiculo(10);
        when(veiculoRepository.save(any())).thenReturn(veiculoEntity);

        // Ação (ACT)
        VeiculoDTO veiculoDTO = veiculoService.create(veiculoCreateDTO);

        // Verificação (ASSERT)
//        assertTrue(pessoaDTO != null);
        Assertions.assertNotNull(veiculoDTO);
        Assertions.assertNotNull(veiculoDTO.getIdVeiculo());
        Assertions.assertEquals("Honda", veiculoDTO.getMarca());
    }

    @Test
    public void deveTestarListComSucesso(){
        // Criar variaveis (SETUP)
        // pessoaRepository.findAll()
        List<VeiculoEntity> lista = new ArrayList<>();
        lista.add(getVeiculoEntity());
        when(veiculoRepository.findAll()).thenReturn(lista);

        // Ação (ACT)
        List<VeiculoDTO> list = veiculoService.list();

        // Verificação (ASSERT)
        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.size() > 0);
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        //pessoaRepository.findById(id)
        VeiculoEntity veiculoEntity = getVeiculoEntity();
        veiculoEntity.setIdVeiculo(10);
        when(veiculoRepository.findById(anyInt())).thenReturn(Optional.of(veiculoEntity));

        // Ação (ACT)
        VeiculoDTO veiculoDTO = veiculoService.findById(busca, false);

        // Verificação (ASSERT)
        Assertions.assertNotNull(veiculoDTO);
        Assertions.assertEquals(10, veiculoDTO.getIdVeiculo());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(veiculoRepository.findById(anyInt())).thenReturn(Optional.empty());


        // Ação (ACT)
        veiculoService.findById(busca, false);
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer id = 10;

        //pessoaRepository.findById(id)
        VeiculoEntity veiculoEntity = getVeiculoEntity();
        veiculoEntity.setIdVeiculo(10);
        when(veiculoRepository.findById(anyInt())).thenReturn(Optional.of(veiculoEntity));

        // Ação (ACT)
        veiculoService.delete(id);

        // Verificação (ASSERT)
        // verificar se chamou pelo menos 1 vez o metodo pessoaRepository.delete(pessoaEntityRecuperada);
        verify(veiculoRepository, times(1)).delete(any());

        // emailService.sendEmail(pessoaEntityRecuperada, base);
        verify(emailService, times(1)).sendEmail(any(), any());
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id= 10;
        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        // findById(id);
        PessoaEntity pessoaEntity1 = getPessoaEntity();
        pessoaEntity1.setNome("Fulano de tal");
        pessoaEntity1.setIdPessoa(10);
        when(pessoaRepository.findById(anyInt())).thenReturn(Optional.of(pessoaEntity1));

        // pessoaRepository.save(pessoaEntityRecuperada);
        PessoaEntity pessoaEntity = getPessoaEntity();
        when(pessoaRepository.save(any())).thenReturn(pessoaEntity);

        // ACT
        PessoaDTO pessoaDTO = pessoaService.update(id, pessoaCreateDTO);

        // ASSERT
        assertNotNull(pessoaDTO);
        assertNotEquals("Fulano de tal", pessoaDTO.getNome());
    }

    @Test
    public void deveTestarListPaginadoComSucesso(){
        // SETUP
        Integer pagina = 10;
        Integer quantidade = 5;

        //pessoaRepository.findAll(pageable);
        PessoaEntity pessoaEntity = getPessoaEntity();
        Page<PessoaEntity> paginaMock = new PageImpl<>(List.of(pessoaEntity));
        when(pessoaRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);

        // ACT
        Page<PessoaEntity> paginaSolicitada = pessoaService.listPaginado(pagina, quantidade);

        // ASSERT
        assertNotNull(paginaSolicitada);
        assertEquals(1, paginaSolicitada.getSize());
        assertEquals(1, paginaSolicitada.getTotalElements());
    }

    private static VeiculoEntity getVeiculoEntity() {
        VeiculoEntity veiculoEntity = new VeiculoEntity();
        veiculoEntity.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.DISPONIVEL);
        veiculoEntity.setAno(2022);
        veiculoEntity.setCor("Preto");
        veiculoEntity.setMarca("Honda");
        veiculoEntity.setModelo("Fit");
        veiculoEntity.setPlaca("KLA3030");
        veiculoEntity.setQuilometragem(2500.0);
        veiculoEntity.setValorLocacao(220.0);
        return veiculoEntity;
    }

    private static VeiculoCreateDTO getVeiculoCreateDTO() {
        VeiculoCreateDTO veiculoCreateDTO = new VeiculoCreateDTO();
        veiculoCreateDTO.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.DISPONIVEL);
        veiculoCreateDTO.setAno(2022);
        veiculoCreateDTO.setCor("Preto");
        veiculoCreateDTO.setMarca("Honda");
        veiculoCreateDTO.setModelo("Fit");
        veiculoCreateDTO.setPlaca("KLA3030");
        veiculoCreateDTO.setQuilometragem(2500.0);
        veiculoCreateDTO.setValorLocacao(220.0);
        return veiculoCreateDTO;
    }
}
}
