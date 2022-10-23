package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.model.CartaoCredito;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Cliente;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocacaoCreateDTO {
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private Double valorLocacao;
    private Cliente cliente;
    private Veiculo veiculo;
    private CartaoCredito cartaoCredito;
    private Funcionario funcionario;
}
