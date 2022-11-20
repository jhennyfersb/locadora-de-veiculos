package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.VeiculoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

public class VeiculoFactory {
    public static VeiculoEntity getVeiculoEntity(){
        return new VeiculoEntity(2,
                "Honda",
                "Fit",
                "Branco",
                2022,
                2500.0,
                220.0,
                DisponibilidadeVeiculo.DISPONIVEL,
                "KLA3030",
                Collections.emptySet());
    }
    public static VeiculoDTO getVeiculoDTO(){
        return new ObjectMapper().convertValue(getVeiculoEntity(), VeiculoDTO.class);
    }
    public static VeiculoCreateDTO getVeiculoCreateDTO(){
        return new VeiculoCreateDTO("Honda",
                "Fit",
                "Branco",
                2022,
                2500.0,
                220.0,
                "KLA3030",
                DisponibilidadeVeiculo.DISPONIVEL);
    }
}
