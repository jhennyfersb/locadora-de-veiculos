package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.model.DisponibilidadeVeiculo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VeiculoCreateDTO {
    private String marca;
    private String modelo;
    private String cor;
    private Integer ano;
    private Double quilometragem;
    private Double valorLocacao;
    private DisponibilidadeVeiculo disponibilidadeVeiculo;
    private String placa;
}
