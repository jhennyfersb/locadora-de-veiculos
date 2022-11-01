package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "veiculo")
public class VeiculoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_VEICULO")
    @SequenceGenerator(name = "SEQ_VEICULO",sequenceName = "seq_veiculo",allocationSize = 1)
    @Column(name = "id_veiculo")
    private Integer idVeiculo;
    private String marca;
    private String modelo;
    private String cor;
    private Integer ano;
    private Double quilometragem;
    @Column(name = "valor_locacao_diario")
    private Double valorLocacao;
    @Column(name = "disponibilidade")
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
