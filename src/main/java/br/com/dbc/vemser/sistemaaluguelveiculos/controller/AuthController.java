package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.*;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.security.TokenService;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.FuncionarioService;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final FuncionarioService funcionarioService;
    private final TokenService tokenService;
    private final AuthService authService;


    @PostMapping("/create")
    public ResponseEntity<FuncionarioDTO> create(@RequestBody @Valid FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        FuncionarioDTO funcionarioDTO = funcionarioService.create(funcionarioCreateDTO);
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.OK);
    }

    @PostMapping
    public String auth(@RequestBody @Valid LoginCreateDTO loginDTO) {
        FuncionarioEntity funcionarioEntity = authService.auth(loginDTO);
        return tokenService.getToken(funcionarioEntity, null);
    }

    @GetMapping
    public ResponseEntity<LoginDTO> retornarId() throws RegraDeNegocioException {
        return new ResponseEntity<>(funcionarioService.getLoggedUser(), HttpStatus.OK);
    }

    @PostMapping("/solicitar-troca-senha/{cpf}")
    public void trocarSenha(@PathVariable("cpf")String cpf) throws RegraDeNegocioException {

        authService.trocarSenha(cpf);
        new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/trocar-senha")
    public void trocarSenhaAuntenticado(@RequestBody @Valid UserSenhaDTO userSenhaDTO) throws RegraDeNegocioException {
        String cpf = authService.procurarUsuario();
        funcionarioService.atualizarSenhaFuncionario(cpf, userSenhaDTO.getSenha());
        new ResponseEntity<>(null, HttpStatus.OK);
    }

}
