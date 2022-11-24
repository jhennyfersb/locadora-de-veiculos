package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CupomDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "locacao")
public class LocacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOCACAO")
    @SequenceGenerator(name = "SEQ_LOCACAO", sequenceName = "seq_locacao", allocationSize = 1)

    @Column(name = "id_locacao")
    private Integer idLocacao;

    @Column(name = "data_locacao")
    private LocalDate dataLocacao;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Column(name = "valor_locacao_total")
    private Double valorLocacao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private ClienteEntity clienteEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veiculo", referencedColumnName = "id_veiculo")
    private VeiculoEntity veiculoEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cartao", referencedColumnName = "id_cartao")
    private CartaoCreditoEntity cartaoCreditoEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id_funcionario")
    private FuncionarioEntity funcionarioEntity;

    public LocacaoEntity(Integer idLocacao,
                         LocalDate dataLocacao,
                         LocalDate dataDevolucao,
                         ClienteEntity clienteEntity,
                         VeiculoEntity veiculo,
                         CartaoCreditoEntity cartaoCreditoEntity,
                         FuncionarioEntity funcionarioEntity) {
        this.idLocacao = idLocacao;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.clienteEntity = clienteEntity;
        this.veiculoEntity = veiculo;
        this.cartaoCreditoEntity = cartaoCreditoEntity;
        this.funcionarioEntity = funcionarioEntity;
    }

}
