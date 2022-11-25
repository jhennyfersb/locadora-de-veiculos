package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.LogControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogDTOContador;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/log")

public class LogController implements LogControllerInterface {

    private final LogService logService;

    @GetMapping("/list")
    public List<LogDTO> list() {
        return logService.listAllLogs();
    }

    @GetMapping("/list-by-tipo-log")
    public List<LogDTO> listByTipoLog(TipoLog tipoLog) {
        return logService.listLogsByTipoLog(tipoLog);
    }

    @GetMapping("/count-logs-by-tipo")
    public List<LogDTOContador> getCountByTipoLog() {
        return logService.countLogsByTipo();
    }

}
