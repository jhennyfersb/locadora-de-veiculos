package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.AuthControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.UserSenhaDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.security.TokenService;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.AuthService;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements AuthControllerInterface {
    private final FuncionarioService funcionarioService;
    private final TokenService tokenService;
    private final AuthService authService;


    @PostMapping
    public String auth(@RequestBody @Valid LoginCreateDTO loginDTO) {
        FuncionarioEntity funcionarioEntity = authService.auth(loginDTO);
        return tokenService.getToken(funcionarioEntity, null);
    }

    @GetMapping
    public ResponseEntity<LoginDTO> retornarUsuarioLogado() {
        return new ResponseEntity<>(funcionarioService.getLoggedUser(), HttpStatus.OK);
    }

    @PostMapping("/solicitar-troca-senha/{cpf}")
    public void trocarSenha(@PathVariable("cpf") String cpf) throws RegraDeNegocioException {
        authService.trocarSenha(cpf);
        new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/trocar-senha")
    public void trocarSenhaAuntenticado(@RequestBody @Valid UserSenhaDTO userSenhaDTO) throws RegraDeNegocioException {
        String cpf = authService.procurarUsuario(userSenhaDTO.getToken());
        funcionarioService.atualizarSenhaFuncionario(cpf, userSenhaDTO.getSenha());
        new ResponseEntity<>(null, HttpStatus.OK);
    }

}
