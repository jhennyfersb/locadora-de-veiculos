package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.PageDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface VeiculoControllerInterface {
    @Operation(summary = "Retornar veículo com id específico.", description = "Retorna veículo com id especificado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna veículo com id especificado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    VeiculoDTO listByIdVeiculo(@PathVariable("idVeiculo") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Retornar veículo por disponibilidade.", description = "Retorna veículo por disponibilidade.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna veículo por disponibilidade."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<VeiculoDTO> listarVeiculosPorDisponibilidade(@RequestParam("disponibilidade") DisponibilidadeVeiculo disponibilidade) throws RegraDeNegocioException;

    @Operation(summary = "Listar veículos", description = "Lista todas as veículos do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de veículos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    PageDTO<VeiculoDTO> list(@RequestParam Integer pagina,
                             @RequestParam Integer tamanho) throws RegraDeNegocioException;

    @Operation(summary = "Criar um registro de veículo.", description = "Cria um cadastro de veículo no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um registro de veículo."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<VeiculoDTO> create(@Valid @RequestBody VeiculoCreateDTO veiculoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar um registro de veículo.", description = "Atualiza um cadastro de veículo no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza um registro de veículo."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<VeiculoDTO> update(@PathVariable("idVeiculo") Integer idVeiculo,
                                      @Valid @RequestBody VeiculoCreateDTO veiculoAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Excluir um registro de veículo.", description = "Exclui um cadastro de veículo no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui um registro de veículo."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<VeiculoDTO> delete(@PathVariable("idVeiculo") Integer idVeiculo) throws RegraDeNegocioException;
}
