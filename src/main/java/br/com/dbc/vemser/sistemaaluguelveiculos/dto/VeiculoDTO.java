package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.DisponibilidadeVeiculo;
import lombok.Data;

@Data
public class VeiculoDTO extends VeiculoCreateDTO{
    private Integer idVeiculo;
}
