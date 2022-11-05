package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class VeiculoCreateDTO {

    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(description = "Marca do veículo.", example="Chevrolet")
    private String marca;

    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(description = "Modelo do veículo.", example="Corsa Premium")
    private String modelo;

    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(description = "Cor do veículo.", example="Preto")
    private String cor;

    @NotNull
    @Schema(description = "Ano do veículo.", example= "2009")
    private Integer ano;

    @NotNull
    @Schema(description = "Quilometragem do veículo.", example="87000")
    private Double quilometragem;

    @NotNull
    @Schema(description = "Valor de locação do veículo.", example="110")
    private Double valorLocacao;

    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(description = "Placa do veículo.", example="PID2836")
    private String placa;

    @NotNull
    @Schema(description = "Valor de locação do veículo.", example="DISPONIVEL")
    private DisponibilidadeVeiculo disponibilidadeVeiculo;

}
