package br.com.dbc.vemser.sistemaaluguelveiculos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartaoCredito {
    private Integer idCartaoCredito;
    private String numero;
    private BandeiraCartao bandeiraCartao;
    private String validade;
    private Double limite;
}
