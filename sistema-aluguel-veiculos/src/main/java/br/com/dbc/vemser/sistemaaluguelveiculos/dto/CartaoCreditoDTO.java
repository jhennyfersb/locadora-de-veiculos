package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.model.BandeiraCartao;
import lombok.Data;

@Data
public class CartaoCreditoDTO {
    private Integer idCartaoCredito;
    private String numero;
    private BandeiraCartao bandeiraCartao;
    private String validade;
    private Double limite;
}
