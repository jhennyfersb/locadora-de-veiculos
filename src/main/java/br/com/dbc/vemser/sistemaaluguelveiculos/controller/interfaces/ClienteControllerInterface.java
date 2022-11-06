package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;


import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ClienteControllerInterface {
    @Operation(summary = "Retornar cliente com id específico.", description = "Retorna cliente com id especificado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cliente com id especificado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ClienteDTO listByIdCliente(Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Relatorio de dados do cliente com id específico.", description = "Relatorio cliente com id especificado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna relatorio do cliente com id especificado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<RelatorioClienteDTO> relatorioCliente(@RequestParam(required = false) Integer idCliente);

    @Operation(summary = "Listar clientes", description = "Lista todos clientes do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<ClienteDTO> list() throws RegraDeNegocioException;

    @Operation(summary = "Criar o registro de um cliente.", description = "Cria um cadastro de cliente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um registro de cliente."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar o registro de um cliente.", description = "Atualiza um cadastro de cliente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza um registro de cliente."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<ClienteDTO> update(@PathVariable("idCliente") Integer id,
                                      @Valid @RequestBody ClienteCreateDTO clienteAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Excluir o registro de um cliente.", description = "Exclui um cadastro de cliente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui um registro de cliente."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<ClienteDTO> delete(@PathVariable("idCliente") Integer id) throws RegraDeNegocioException;
}
