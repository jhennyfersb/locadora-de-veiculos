package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.DisponibilidadeVeiculo;
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
    @Schema(description = "Marca do veículo.", example="Honda")
    private String marca;

    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(description = "Modelo do veículo.", example="Fit")
    private String modelo;

    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(description = "Cor do veículo.", example="Preto")
    private String cor;

    @NotNull
    @Schema(description = "Ano do veículo.", example= "2021")
    private Integer ano;

    @NotNull
    @Schema(description = "Quilometragem do veículo.", example="1800")
    private Double quilometragem;

    @NotNull
    @Schema(description = "Valor de locação do veículo.", example="220")
    private Double valorLocacao;

    @NotNull
    @Schema(description = "Disponibilidade do veículo.")
    private DisponibilidadeVeiculo disponibilidadeVeiculo;

    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(description = "Placa do veículo.", example="KOT9898")
    private String placa;
}
