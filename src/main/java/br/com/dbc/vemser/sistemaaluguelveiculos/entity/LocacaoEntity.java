package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "LocacaoEntity")
public class LocacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_LOCACAO")
    @SequenceGenerator(name = "SEQ_LOCACAO",sequenceName = "seq_locacao",allocationSize = 1)
    @Column(name = "id_locacao")
    private Integer idLocacao;
    @Column(name = "data_locacao")
    private LocalDate dataLocacao;
    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Column(name = "valor_locacao_total")
    private Double valorLocacao;

    private Cliente cliente;
    private Veiculo veiculo;
    private CartaoCreditoEntity cartaoCreditoEntity;
    private Funcionario funcionario;

    public LocacaoEntity(Integer idLocacao,
                         LocalDate dataLocacao,
                         LocalDate dataDevolucao,
                         Cliente cliente,
                         Veiculo veiculo,
                         CartaoCreditoEntity cartaoCreditoEntity,
                         Funcionario funcionario) {
        this.idLocacao = idLocacao;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.cartaoCreditoEntity = cartaoCreditoEntity;
        this.funcionario = funcionario;
    }
}
