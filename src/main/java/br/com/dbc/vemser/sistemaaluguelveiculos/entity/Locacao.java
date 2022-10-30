package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class Locacao {
    private Integer idLocacao;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private Double valorLocacao;
    private Cliente cliente;
    private Veiculo veiculo;
    private CartaoCredito cartaoCredito;
    private Funcionario funcionario;

    public Locacao(Integer idLocacao,
                   LocalDate dataLocacao,
                   LocalDate dataDevolucao,
                   Cliente cliente,
                   Veiculo veiculo,
                   CartaoCredito cartaoCredito,
                   Funcionario funcionario) {
        this.idLocacao = idLocacao;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.cartaoCredito = cartaoCredito;
        this.funcionario = funcionario;
    }
}
