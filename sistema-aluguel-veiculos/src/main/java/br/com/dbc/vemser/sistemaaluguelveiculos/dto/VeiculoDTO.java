package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.model.DisponibilidadeVeiculo;
import lombok.Data;

@Data
public class VeiculoDTO {
    private Integer idVeiculo;
    private String marca;
    private String modelo;
    private String cor;
    private Integer ano;
    private Double quilometragem;
    private Double valorLocacao;
    private DisponibilidadeVeiculo disponibilidadeVeiculo;
    private String placa;
}
