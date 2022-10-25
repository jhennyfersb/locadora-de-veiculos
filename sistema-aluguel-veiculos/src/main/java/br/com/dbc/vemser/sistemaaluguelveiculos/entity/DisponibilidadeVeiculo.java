package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

public enum DisponibilidadeVeiculo {
    ALUGADO(1),
    DISPONIVEL(2);

    private Integer disponibilidade;

    DisponibilidadeVeiculo(Integer disponibilidade){
        this.disponibilidade = disponibilidade;
    }

    public Integer getDisponibilidade() {
        return disponibilidade;
    }
}


