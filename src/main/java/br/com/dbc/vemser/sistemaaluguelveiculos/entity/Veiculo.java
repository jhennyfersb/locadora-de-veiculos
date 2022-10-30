package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Veiculo {
    private Integer idVeiculo;
    private String marca;
    private String modelo;
    private String cor;
    private Integer ano;
    private Double quilometragem;
    private Double valorLocacao;
    private DisponibilidadeVeiculo disponibilidadeVeiculo;
    private String placa;

    public void alterarDisponibilidadeVeiculo() {
        if (this.disponibilidadeVeiculo.getDisponibilidade() == 1) {
            this.disponibilidadeVeiculo = DisponibilidadeVeiculo.DISPONIVEL;
        } else if (this.disponibilidadeVeiculo.getDisponibilidade() == 2) {
            this.disponibilidadeVeiculo = DisponibilidadeVeiculo.ALUGADO;
        }
    }
}
