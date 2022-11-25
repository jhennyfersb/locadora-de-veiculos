package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioCupom;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioCupomDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface RelatorioCupomControllerInterface {
    @Operation(summary = "Retornar lista de status dos cupoms.", description = "Retorna lista de status dos cupoms")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna lista de status dos cupoms."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<RelatorioCupomDTO> relatorioCupomStatus();
}
