package br.com.dbc.vemser.sistemaaluguelveiculos.security;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String getToken(FuncionarioEntity funcionarioEntity){
        LocalDateTime localDateTimeAtual = LocalDateTime.now();
        Date dataAtual = Date.from(localDateTimeAtual.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime localDateTimeExpirar = localDateTimeAtual.plusDays(Long.parseLong(expiration));
        Date expiracao = Date.from(localDateTimeExpirar.atZone(ZoneId.systemDefault()).toInstant());
//        // FIXME por meio do usuário, gerar um token


        return Jwts.builder().
                setIssuer("vemser-api")
                .claim(Claims.ID, funcionarioEntity.getCpf())
                .setIssuedAt(dataAtual)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UsernamePasswordAuthenticationToken isValid (String token){
        // FIXME validar se o token é válido e retornar o usuário se for válido
        if(token == null){
            return null;
        }
        token = token.replace("Bearer ", "");
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String user = claims.get(Claims.ID, String.class);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
