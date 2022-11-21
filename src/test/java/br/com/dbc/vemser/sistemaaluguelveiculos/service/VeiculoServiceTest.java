package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.PageDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.VeiculoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.factory.VeiculoFactory;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.VeiculoRepository;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class VeiculoServiceTest {
    @InjectMocks
    private VeiculoService veiculoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private LogService logService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(veiculoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        Integer id = 10;
        VeiculoCreateDTO veiculoCreateDTO = VeiculoFactory.getVeiculoCreateDTO();
        VeiculoEntity veiculoEntity = VeiculoFactory.getVeiculoEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        veiculoEntity.setIdVeiculo(id);
        when(veiculoRepository.save(any())).thenReturn(veiculoEntity);

        VeiculoDTO veiculoDTO = veiculoService.create(veiculoCreateDTO);

        Assertions.assertNotNull(veiculoDTO);
        Assertions.assertNotNull(veiculoDTO.getIdVeiculo());
        Assertions.assertEquals("Honda", veiculoDTO.getMarca());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarListComSucesso() throws RegraDeNegocioException {
        Integer pagina = 10;
        Integer quantidade = 5;
        VeiculoEntity veiculoEntity = VeiculoFactory.getVeiculoEntity();
        Page<VeiculoEntity> paginaMock = new PageImpl<>(List.of(veiculoEntity));

        when(veiculoRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);

        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        PageDTO<VeiculoDTO> paginaSolicitada = veiculoService.list(pagina, quantidade);

        Assertions.assertNotNull(paginaSolicitada);
        Assertions.assertEquals(1, paginaSolicitada.getQuantidadePaginas());
        Assertions.assertEquals(1, paginaSolicitada.getTotalElementos());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        Integer busca = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        VeiculoEntity veiculoEntity = VeiculoFactory.getVeiculoEntity();
        veiculoEntity.setIdVeiculo(busca);
        when(veiculoRepository.findById(anyInt())).thenReturn(Optional.of(veiculoEntity));


        VeiculoDTO veiculoDTO = veiculoService.findDtoById(busca);


        Assertions.assertNotNull(veiculoDTO);
        Assertions.assertEquals(10, veiculoDTO.getIdVeiculo());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        Integer id = 10;
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        VeiculoEntity veiculoEntity = VeiculoFactory.getVeiculoEntity();
        veiculoEntity.setIdVeiculo(id);
        when(veiculoRepository.findById(anyInt())).thenReturn(Optional.of(veiculoEntity));

        veiculoService.delete(id);

        verify(veiculoRepository, times(1)).deleteById(any());
        verify(logService, times(1)).salvarLog(any());
    }

    @Test
    public void deveTestarUpdateComSucesso() throws RegraDeNegocioException {
        Integer id = 10;
        String modelo = "Civic";
        VeiculoCreateDTO veiculoCreateDTO = VeiculoFactory.getVeiculoCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());

        VeiculoEntity veiculoEntity = VeiculoFactory.getVeiculoEntity();
        veiculoEntity.setModelo(modelo);
        veiculoEntity.setIdVeiculo(id);
        when(veiculoRepository.findById(anyInt())).thenReturn(Optional.of(veiculoEntity));
        when(veiculoRepository.save(any())).thenReturn(VeiculoFactory.getVeiculoEntity());

        VeiculoDTO veiculoDTO = veiculoService.update(id, veiculoCreateDTO);


        Assertions.assertNotNull(veiculoDTO);
        Assertions.assertNotEquals("Civic", veiculoDTO.getModelo());
        verify(logService, times(1)).salvarLog(any());
    }


    private static UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(1,
                null,
                Collections.emptyList());
    }
}

