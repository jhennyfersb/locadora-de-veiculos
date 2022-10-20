package br.com.dbc.vemser.sistemaaluguelveiculos.model;

import java.util.Arrays;

public enum DisponibilidadeVeiculo {
    ALUGADO(1),
    DISPONIVEL(2);

    private Integer disponibilidade;

    DisponibilidadeVeiculo(Integer disponibilidade){
        this.disponibilidade = disponibilidade;
    }

    public int getDisponibilidade() {
        return disponibilidade;
    }

    public static DisponibilidadeVeiculo getByValue(Integer numero){
        return Arrays.stream(DisponibilidadeVeiculo.values()).filter(disponibilidadeVeiculo -> disponibilidadeVeiculo.getDisponibilidade()== numero).findFirst().get();
    }
}


