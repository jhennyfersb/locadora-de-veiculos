package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface LocacaoControllerInterface {
    @Operation(summary = "Retornar locação com id específico.", description = "Retorna locação com id especificado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna locação com id especificado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idLocacao}")
    LocacaoDTO listByIdLocacao(Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Listar locações", description = "Lista todas as locações do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de locações"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<LocacaoDTO> list() throws RegraDeNegocioException;
    @Operation(summary = "Relatorio de locações por cidade", description = "Relatorio de todas as locações do banco de dados por cidade.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna relatorio de locações por cidade"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<RelatorioLocacaoPorCidadeDTO> locacaoPorCidade();
    @Operation(summary = "Relatorio de locações por id", description = "Relatorio de todas as locações do banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna relatorio de locações"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    List<RelatorioLocacaoDTO> listarRelatoriosLocacao(@RequestParam Integer idCliente, Integer idveiculo, Integer idFuncionario);
    @Operation(summary = "Relatario quantidade de locações por cliente", description = "Relatorio quantidade de locações por cliente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o relatorio quantidade de locações por clientes"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping
    List<RelatorioLocacaoPorClienteDTO> locacaoPorClienteQuantidade();

    @Operation(summary = "Criar um registro de locação.", description = "Cria um cadastro de locação no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um registro de locação."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<LocacaoDTO> create(@Valid @RequestBody LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar um registro de locação.", description = "Atualiza um cadastro de locação no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza um registro de locação."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idLocacao}")
    ResponseEntity<LocacaoDTO> update(@PathVariable("idLocacao") Integer id,
                                             @Valid @RequestBody LocacaoCreateDTO locacaoAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Excluir um registro de locação.", description = "Exclui um cadastro de locação no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Exclui um registro de locação."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idLocacao}")
    ResponseEntity<LocacaoDTO> delete(@PathVariable("idLocacao") Integer id) throws RegraDeNegocioException;
}
