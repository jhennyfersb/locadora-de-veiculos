package br.com.dbc.vemser.sistemaaluguelveiculos.service;


import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogDTOContador;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.LogEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    SimpleDateFormat sdfComplete = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public void salvarLog(LogCreateDTO logDTO) {
        var log = new LogEntity();
        BeanUtils.copyProperties(logDTO, log);
        log.setData(sdfComplete.format(new Date()));
        logRepository.save(log);
    }

    public List<LogDTO> listAllLogs() {
        return logRepository.findAll().stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
    }

    public List<LogDTO> listLogsByTipoLog(TipoLog tipoLog) {
        return logRepository.findAllByTipoLog(tipoLog).stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
    }

    public LogDTOContador countLogsByTipo(TipoLog tipoLog) {
        LogDTOContador log = new LogDTOContador();
        log.setQuantidade(logRepository.countByTipoLog(tipoLog));
        log.setTipoLog(tipoLog);
        return log;
    }
}
