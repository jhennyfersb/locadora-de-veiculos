package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.BandeiraCartao;
import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "cartaoCredito")
public class CartaoCreditoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_CARTAO_CREDITO")
    @SequenceGenerator(name = "SEQ_CARTAO_CREDITO",sequenceName = "seq_cartao_credito",allocationSize = 1)
    @Column(name = "id_cartao")
    private Integer idCartaoCredito;

    @Column(name = "numero_cartao")
    private String numero;

    @Column(name = "bandeira_cartao")
    @Enumerated(EnumType.ORDINAL)
    private BandeiraCartao bandeiraCartao;


    private String validade;
    private Double limite;
}
