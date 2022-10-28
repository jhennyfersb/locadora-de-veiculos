package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.BandeiraCartao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CartaoCreditoCreateDTO {

    @NotEmpty
    @NotBlank
    @NotNull
    @Schema(description = "Número do cartão de crédito.", example="9595 9898 9492 8788")
    private String numero;

    @NotNull
    @Schema(description = "Bandeira do cartão de crédito.")
    private BandeiraCartao bandeiraCartao;

    @NotEmpty
    @NotBlank
    @NotNull
    @Schema(description = "Data de validade do cartão.", example="09/2030")
    private String validade;

    @NotNull
    @Schema(description = "Limite do cartão.", example="60000")
    private Double limite;
}
