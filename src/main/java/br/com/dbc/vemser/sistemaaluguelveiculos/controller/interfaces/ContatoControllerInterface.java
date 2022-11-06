package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ContatoControllerInterface {
    @Operation(summary = "Retornar contato com id específico.", description = "Retorna contato com id especificado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna contato com id especificado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ContatoDTO listByIdContato(Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Listar contatos", description = "Lista todos contatos do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de contatos."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<ContatoDTO> list() throws RegraDeNegocioException;

    @Operation(summary = "Criar o registro de um contato.", description = "Cria um cadastro de contato no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um registro de contato."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ContatoDTO> create(@Valid @RequestBody ContatoCreateDTO contatoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar o registro de um contato.", description = "Atualiza um cadastro de contato no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza um registro de contato."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<ContatoDTO> update(@PathVariable("idContato") Integer id,
                                      @Valid @RequestBody ContatoCreateDTO contatoAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Excluir o registro de um contato.", description = "Exclui um cadastro de contato no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui um registro de contato."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<ContatoDTO> delete(@PathVariable("idContato") Integer id) throws RegraDeNegocioException;
}
