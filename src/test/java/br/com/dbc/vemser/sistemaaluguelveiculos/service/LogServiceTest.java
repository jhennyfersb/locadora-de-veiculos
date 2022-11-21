package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogDTOContador;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.LogEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.LogRepository;
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
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {
    @InjectMocks
    private LogService logService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private LogRepository logRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(logService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarSalvarLogComSucesso() {
        LogCreateDTO logCreateDTO = getLogCreateDTO();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        SimpleDateFormat sdfComplete = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        var log = new LogEntity();
        BeanUtils.copyProperties(logCreateDTO, log);
        log.setData(sdfComplete.format(new Date()));
        log.setId("11122233344");

        when(logRepository.save(any())).thenReturn(log);

        logService.salvarLog(logCreateDTO);

        verify(logRepository, times(1)).save(any());
    }

    @Test
    public void deveTestarListComSucesso() {
        LogEntity logEntity = getLogEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        List<LogEntity> list = new ArrayList<>();
        list.add(logEntity);

        when(logRepository.findAll()).thenReturn(list);


        List<LogDTO> logDTOList = logService.listAllLogs();

        Assertions.assertNotNull(logDTOList);
        Assertions.assertTrue(logDTOList.size() > 0);
        Assertions.assertEquals(1, logDTOList.size());
    }

    @Test
    public void deveTestarListLogsByTipoLogComSucesso() {
        LogEntity logEntity = getLogEntity();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        List<LogEntity> list = new ArrayList<>();
        list.add(logEntity);

        when(logRepository.findAllByTipoLog(TipoLog.CREATE)).thenReturn(list);

        List<LogDTO> logDTOList = logService.listLogsByTipoLog(TipoLog.CREATE);

        Assertions.assertNotNull(logDTOList);
        Assertions.assertTrue(logDTOList.size() > 0);
        Assertions.assertEquals(1, logDTOList.size());
    }

    @Test
    public void deveTetarCountLogsByTipoComSucesso(){
        LogDTOContador log = new LogDTOContador();
        SecurityContextHolder.getContext().setAuthentication(getAuthentication());
        log.setQuantidade(logRepository.countByTipoLog(TipoLog.CREATE));
        log.setTipoLog(TipoLog.CREATE);

        when(logRepository.countByTipoLog(TipoLog.CREATE)).thenReturn(1);

        LogDTOContador logDTOContador = logService.countLogsByTipo(TipoLog.CREATE);

        Assertions.assertNotNull(logDTOContador);
        Assertions.assertEquals(1, logDTOContador.getQuantidade());
    }


    private static LogEntity getLogEntity() {
        LogEntity logEntity = new LogEntity();
        logEntity.setTipoLog(TipoLog.CREATE);
        logEntity.setDescricao("teste");
        logEntity.setEntityLog(EntityLog.FUNCIONARIO);
        logEntity.setId("11122233344");
        logEntity.setData("19/02/2022");
        return logEntity;
    }

    private static LogCreateDTO getLogCreateDTO() {
        LogCreateDTO logCreateDTO = new LogCreateDTO();
        logCreateDTO.setTipoLog(TipoLog.CREATE);
        logCreateDTO.setDescricao("teste");
        logCreateDTO.setEntityLog(EntityLog.FUNCIONARIO);
        return logCreateDTO;
    }

    private static UsernamePasswordAuthenticationToken getAuthentication(){
        return new UsernamePasswordAuthenticationToken(1,
                null,
                Collections.emptyList());
    }
}

