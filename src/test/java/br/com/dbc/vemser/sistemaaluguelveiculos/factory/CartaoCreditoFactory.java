package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCreditoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.BandeiraCartao;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

public class CartaoCreditoFactory {
    public static CartaoCreditoEntity getCartaoCreditoEntity(){
        return new CartaoCreditoEntity(4,
                "9595 9898 9492 8788",
                BandeiraCartao.VISA,
                "05/2030",
                50000.00,
                Collections.emptySet());
    }
    public static CartaoCreditoDTO getCartaoCreditoCreateDTO(){
        return new ObjectMapper().convertValue(getCartaoCreditoEntity(), CartaoCreditoDTO.class);
    }

}
