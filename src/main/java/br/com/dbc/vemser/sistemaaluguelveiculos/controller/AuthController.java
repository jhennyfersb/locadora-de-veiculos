package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.security.TokenService;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.FuncionarioService;
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

    private final AuthenticationManager authenticationManager;

    @PostMapping("/create")
    public ResponseEntity<FuncionarioDTO> create (@RequestBody @Valid FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        FuncionarioDTO funcionarioDTO = funcionarioService.create(funcionarioCreateDTO);
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.OK);
    }

    @PostMapping
    public String auth(@RequestBody @Valid LoginCreateDTO loginDTO) throws RegraDeNegocioException {
        // FIXME adicionar mecanismo de autenticação para verificar se o usuário é válido e retornar o token
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getCpf(),
                loginDTO.getSenha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Object principal = authentication.getPrincipal();
        FuncionarioEntity funcionarioEntity = (FuncionarioEntity) principal;
        return tokenService.getToken(funcionarioEntity);
    }

    @GetMapping
    public ResponseEntity<LoginDTO> retornarId() throws RegraDeNegocioException {
        return new ResponseEntity<>(funcionarioService.getLoggedUser(), HttpStatus.OK);
    }
}
