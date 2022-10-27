package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.BandeiraCartao;
import lombok.Data;

@Data
public class CartaoCreditoDTO extends CartaoCreditoCreateDTO{
    private Integer idCartaoCredito;
}
