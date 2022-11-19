package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.VeiculoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

public class VeiculoFactory {
    public static VeiculoEntity getVeiculo(){
        return new VeiculoEntity(2,
                "Honda",
                "Civic",
                "Branco",
                2005,
                1200.00,
                240.00,
                DisponibilidadeVeiculo.DISPONIVEL,
                "AUR1234",
                Collections.emptySet());
    }
    public static VeiculoDTO getVeiculoDTO(){
        return new ObjectMapper().convertValue(getVeiculo(), VeiculoDTO.class);
    }
}
