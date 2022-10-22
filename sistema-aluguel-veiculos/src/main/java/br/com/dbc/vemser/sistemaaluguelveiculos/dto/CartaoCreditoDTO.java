package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.model.BandeiraCartao;
import lombok.Data;

@Data
public class CartaoCreditoDTO {

    private int idCartaoCredito;
    private String numero;
    private BandeiraCartao bandeira;
    private String validade;
    private double limite;
}
