package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "cor")
    private String cor;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "quilometragem")
    private Double quilometragem;

    @Column(name = "valor_locacao_diario")
    private Double valorLocacao;

    @Column(name = "disponibilidade")
    private DisponibilidadeVeiculo disponibilidadeVeiculo;

    @Column(name = "placa")
    private String placa;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "veiculoEntity", cascade = CascadeType.ALL)
    private Set<LocacaoEntity> locacaoEntities;

    public void alterarDisponibilidadeVeiculo() {
        if (this.disponibilidadeVeiculo.getDisponibilidade() == 1) {
            this.disponibilidadeVeiculo = DisponibilidadeVeiculo.DISPONIVEL;
        } else if (this.disponibilidadeVeiculo.getDisponibilidade() == 2) {
            this.disponibilidadeVeiculo = DisponibilidadeVeiculo.ALUGADO;
        }

    }
}
