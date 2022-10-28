package br.com.dbc.vemser.sistemaaluguelveiculos.controller;


import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ClienteControllerInterface {

    @Operation(summary = "Listar clientes", description = "Lista todos clientes do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping // localhost:8080/cliente OK
    List<ClienteDTO> list() throws BancoDeDadosException;

    @Operation(summary = "Criar o registro de um cliente.", description = "Cria um cadastro de cliente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um registro de cliente."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping // localhost:8080/cliente/4 OK
    ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws Exception;

    @Operation(summary = "Atualizar o registro de um cliente.", description = "Atualiza um cadastro de cliente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza um registro de cliente."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCliente}") // localhost:8080/cliente/1000 OK
    ResponseEntity<ClienteDTO> update(@PathVariable("idCliente") Integer id,
                                             @Valid @RequestBody ClienteCreateDTO clienteAtualizar) throws Exception;

    @Operation(summary = "Excluir o registro de um cliente.", description = "Exclui um cadastro de cliente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui um registro de cliente."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCliente}") // localhost:8080/cliente/10 OK
    ResponseEntity<ClienteDTO> delete(@PathVariable("idCliente") Integer id) throws Exception;
}
