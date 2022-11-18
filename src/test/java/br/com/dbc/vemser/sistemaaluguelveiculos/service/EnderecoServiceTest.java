package br.com.dbc.vemser.sistemaaluguelveiculos.service;


import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.EnderecoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.EnderecoRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnderecoServiceTest {
    @InjectMocks
    private EnderecoService enderecoService;
    @Mock
    private EnderecoRepository enderecoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClienteService clienteService;

    @Before
    public void init(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(clienteService, "objectMapper", objectMapper);
    }
    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        EnderecoCreateDTO enderecoCreateDTO = getEnderecoCreateDTO();
        EnderecoEntity enderecoEntity = getEnderecoEntity();
        enderecoEntity.setIdCliente(10);

        when(enderecoRepository.save(any())).thenReturn(enderecoEntity);

        EnderecoDTO enderecoDTORetorno = enderecoService.create(enderecoCreateDTO);

    }
    private static EnderecoEntity getEnderecoEntity() {
        EnderecoEntity enderecoEntity = new EnderecoEntity(1,
                2,
                "Av Brasil",
                "746",
                "Centro",
                "Pato Branco",
                "Paraná",
                "85501057",
                "perto de");
        return enderecoEntity;
    }
    private static EnderecoCreateDTO getEnderecoCreateDTO() {
        EnderecoCreateDTO enderecoCreateDTO = new EnderecoCreateDTO(1,
                2,
                "Av Brasil",
                "746",
                "Centro",
                "Pato Branco",
                "Paraná",
                "85501057",
                "perto de");
        return enderecoCreateDTO;
    }
}
