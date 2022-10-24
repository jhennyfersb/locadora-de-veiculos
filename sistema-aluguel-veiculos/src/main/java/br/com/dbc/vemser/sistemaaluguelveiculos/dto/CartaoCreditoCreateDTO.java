package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.model.BandeiraCartao;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CartaoCreditoCreateDTO {

    @NotEmpty
    @NotBlank
    @NotNull
    private String numero;

    @NotNull
    private BandeiraCartao bandeiraCartao;

    @NotEmpty
    @NotBlank
    @NotNull
    private String validade;

    @NotNull
    private Double limite;
}
