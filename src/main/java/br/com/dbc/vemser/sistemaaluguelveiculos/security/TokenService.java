package br.com.dbc.vemser.sistemaaluguelveiculos.security;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String KEY_CARGOS = "CARGOS";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String getToken(FuncionarioEntity funcionarioEntity){
        LocalDateTime localDateTimeAtual = LocalDateTime.now();
        Date dataAtual = Date.from(localDateTimeAtual.atZone(ZoneId.systemDefault()).toInstant());
        Date expiracao = Date.from(localDateTimeAtual.plusDays(Integer.parseInt(expiration)).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder().
                setIssuer("vemser-api")
                .claim(Claims.ID, funcionarioEntity.getCpf())
                .claim(KEY_CARGOS,funcionarioEntity.getCargoEntity().getAuthority())
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
        List<String> cargos = claims.get(KEY_CARGOS, List.class);

        List<SimpleGrantedAuthority> listaDeCargos = cargos.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new UsernamePasswordAuthenticationToken(user, null, listaDeCargos);
    }
}
