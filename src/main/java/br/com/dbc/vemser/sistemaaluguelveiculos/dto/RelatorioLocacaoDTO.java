package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RelatorioLocacaoDTO {
    private String nomeCliente;
    private String cpfCliente;
    private String telefone;
    private String email;
    private String cidade;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private double valor;
    private String marca;
    private String modelo;
    private String cor;
    private Integer ano;
    private String placa;
    private double quilometragem;
    private String nomeFuncionario;


}
