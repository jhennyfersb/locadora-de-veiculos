package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.model.DisponibilidadeVeiculo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class VeiculoCreateDTO {

    @NotBlank
    @NotEmpty
    @NotNull
    private String marca;

    @NotBlank
    @NotEmpty
    @NotNull
    private String modelo;

    @NotBlank
    @NotEmpty
    @NotNull
    private String cor;

    @NotNull
    private Integer ano;

    @NotNull
    private Double quilometragem;

    @NotNull
    private Double valorLocacao;

    @NotNull
    private DisponibilidadeVeiculo disponibilidadeVeiculo;

    @NotBlank
    @NotEmpty
    @NotNull
    private String placa;
}
