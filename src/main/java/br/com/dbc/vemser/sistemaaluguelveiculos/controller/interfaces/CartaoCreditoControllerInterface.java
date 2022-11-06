package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface CartaoCreditoControllerInterface {
    @Operation(summary = "Retornar cartão de crédito com id específico.", description = "Retorna cartão de crédito com id específico.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cartão de crédito com id específico."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    CartaoCreditoDTO listByIdCartaoCredito(Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Listar cartões de crédito.", description = "Lista todos os cartões de crédito do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de cartões de crédito."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<CartaoCreditoDTO> list() throws RegraDeNegocioException;

    @Operation(summary = "Criar o registro de um cartão de crédito.", description = "Cria um cadastro de cartão de crédito no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um registro de cartão de crédito."),
                    @ApiResponse(responseCode = "201", description = "Cria um registro de cartão de crédito."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<CartaoCreditoDTO> create(@Valid @RequestBody CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar o registro de um cartão de crédito.", description = "Atualiza um cadastro de cartão de crédito no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza um registro de cartão de crédito."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<CartaoCreditoDTO> update(@PathVariable("idCartao") Integer idCartao,
                                            @Valid @RequestBody CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException;

    @Operation(summary = "Excluir o registro de um cartão de crédito.", description = "Exclui um cadastro de cartão de crédito no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui um registro de cartão de crédito."),
                    @ApiResponse(responseCode = "204", description = "Exclui um registro de cartão de crédito."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<Void> delete(@PathVariable("idCartao") Integer idCartao) throws RegraDeNegocioException;
}
