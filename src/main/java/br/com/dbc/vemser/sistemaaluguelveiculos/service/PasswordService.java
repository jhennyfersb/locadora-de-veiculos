package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.PasswordResetToken;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetTokenForUser(FuncionarioEntity funcionarioEntity, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, funcionarioEntity);
        passwordResetTokenRepository.save(myToken);
    }

    public FuncionarioEntity findFuncionarioByToken(String token) {
        return passwordResetTokenRepository.findByToken(token).get().getFuncionarioEntity();
    }
}
