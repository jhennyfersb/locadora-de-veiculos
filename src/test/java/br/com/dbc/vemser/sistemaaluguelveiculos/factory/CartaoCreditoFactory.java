package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCreditoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.BandeiraCartao;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

public class CartaoCreditoFactory {
    public static CartaoCreditoEntity getCartaoCredito(){
        return new CartaoCreditoEntity(4,
                "9999 8888 2222 5555",
                BandeiraCartao.VISA,
                "05/2030",
                50000.00,
                Collections.emptySet());
    }
    public static CartaoCreditoDTO getcartaoCreditoDTO(){
        return new ObjectMapper().convertValue(getCartaoCredito(), CartaoCreditoDTO.class);
    }
}
