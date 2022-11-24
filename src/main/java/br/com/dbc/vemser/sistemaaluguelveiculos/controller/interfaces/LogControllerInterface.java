package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogDTOContador;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface LogControllerInterface {
    @Operation(summary = "Retornar Lista de todos os logs.", description = "Retorna lista de logs.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de logs."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<LogDTO> list();

    @Operation(summary = "Retornar lista de tipo de log especifico.", description = "Retorna tipo de log especifico.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna tipo de log especifico."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<LogDTO> listByTipoLog(TipoLog tipoLog);
    @Operation(summary = "Retornar quantidades de vezes de log.", description = "Retorna quantidade de logs.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna quantidade de logs."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    LogDTOContador getCountByTipoLog(TipoLog tipoLog);
}
