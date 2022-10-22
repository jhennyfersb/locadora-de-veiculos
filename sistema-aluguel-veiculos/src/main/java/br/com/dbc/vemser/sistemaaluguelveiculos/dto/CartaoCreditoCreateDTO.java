package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.model.BandeiraCartao;

public class CartaoCreditoCreateDTO {

    private String numero;
    private BandeiraCartao bandeira;
    private String validade;
    private double limite;
}
