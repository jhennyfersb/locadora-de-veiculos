package br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.UserSenhaDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthControllerInterface {
    @Operation(summary = "Retornar usuario logado.", description = "Retorna Usuario logado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna usuario logado."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<LoginDTO> retornarUsuarioLogado();

    @Operation(summary = "Enviar um token de acesso temporário para o e-mail cadastrado no cpf especificado.", description = "Envia um token de acesso temporário para o e-mail cadastrado no cpf especificado.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token gerado e enviado com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping({"/solicitar-troca-senha/{cpf}"})
    void trocarSenha(@PathVariable("cpf") String cpf) throws RegraDeNegocioException;

    @Operation(summary = "Logar com um registro de funcionário.", description = "Loga no sistema com um login de funcionário.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loga no sistema com um login de funcionário."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    String auth(@RequestBody @Valid LoginCreateDTO loginDTO);

    @Operation(summary = "Trocar senha com token ", description = "Troca senha utilizando um token valido.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
            }
    )
    @PostMapping
    void trocarSenhaAuntenticado(@RequestBody @Valid UserSenhaDTO userSenhaDTO) throws RegraDeNegocioException;
}
