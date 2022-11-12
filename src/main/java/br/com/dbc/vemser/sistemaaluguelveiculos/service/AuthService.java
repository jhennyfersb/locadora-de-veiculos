package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LoginCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final FuncionarioService funcionarioService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    public FuncionarioEntity auth(LoginCreateDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getCpf(),
                loginDTO.getSenha());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Object principal = authentication.getPrincipal();
        FuncionarioEntity funcionarioEntity = (FuncionarioEntity) principal;
        return funcionarioEntity;
    }
    public void trocarSenha(String cpf) throws RegraDeNegocioException {
        Optional<FuncionarioEntity> funcionarioEntity = funcionarioService.findByLogin(cpf);
        if(funcionarioEntity.isEmpty()){
            throw new RegraDeNegocioException("Funcionario não existe");
        }
        String token = tokenService.getToken(funcionarioEntity.get(),"15");
        String base = "Olá "+funcionarioEntity.get().getNome()+" seu token para trocar de senha é: <br>"+token;
        emailService.sendEmail(base,funcionarioEntity.get().getEmail());
    }
    public String procurarUsuario() throws RegraDeNegocioException {
        String cpf = funcionarioService.getIdLoggedUser();
        if (cpf == null) {
            throw new RegraDeNegocioException("Erro ao procurar usuario");
        }
        return cpf;
    }
}
