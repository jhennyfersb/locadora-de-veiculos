package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoDTO extends VeiculoCreateDTO{
    private Integer idVeiculo;
}
