package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface EnderecoControllerInterface {
    @Operation(summary = "Retornar endereço com id específico.", description = "Retorna endereço com id especificado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna endereço com id especificado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEndereco}")
    EnderecoDTO listByIdEndereco(Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Listar endereços por id cliente.", description = "Lista todos endereços do banco de dados por id cliente.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCliente}")
     List<EnderecoDTO> findEnderecoByIdCliente(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException;

    @Operation(summary = "Listar endereços.", description = "Lista todos endereços do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<EnderecoDTO> list() throws RegraDeNegocioException;

    @Operation(summary = "Criar o registro de um endereço.", description = "Cria um cadastro de endereço no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um registro de endereço."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<EnderecoDTO> create(@Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar o registro de um endereço.", description = "Atualiza um cadastro de endereço no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza um registro de endereço."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEndereco}")
    ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id,
                                             @Valid @RequestBody EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Excluir o registro de um endereço.", description = "Exclui um cadastro de endereço no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui um registro de endereço."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEndereco}")
    ResponseEntity<EnderecoDTO> delete(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException;
}
