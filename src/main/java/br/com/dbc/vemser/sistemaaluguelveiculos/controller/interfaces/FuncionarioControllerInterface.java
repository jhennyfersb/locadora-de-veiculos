package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface FuncionarioControllerInterface {
    @Operation(summary = "Retornar funcionário com id específico.", description = "Retorna funcionário com id especificado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna funcionário com id especificado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idFuncionario}")
    FuncionarioDTO listByIdFuncionario(Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Listar funcionários", description = "Lista todas os funcionários do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de funcionários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<FuncionarioDTO> list() throws RegraDeNegocioException;

    @Operation(summary = "Criar um registro de funcionário.", description = "Cria um cadastro de funcionários no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um registro de funcionário."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<FuncionarioDTO> create(@Valid @RequestBody FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Atualizar um registro de funcionário.", description = "Atualiza um cadastro de funcionários no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza um registro de funcionário."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idFuncionario}")
    ResponseEntity<FuncionarioDTO> update(@PathVariable("idFuncionario") Integer id,
                                                 @Valid @RequestBody FuncionarioCreateDTO funcionarioAtualizar) throws RegraDeNegocioException;


    @Operation(summary = "Excluir um registro de funcionário.", description = "Exclui um cadastro de funcionários no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui um registro de funcionário."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idFuncionario}")
    ResponseEntity<FuncionarioDTO> delete(@PathVariable("idFuncionario") Integer id) throws RegraDeNegocioException;
}
