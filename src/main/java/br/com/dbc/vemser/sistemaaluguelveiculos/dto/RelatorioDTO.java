package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RelatorioDTO {
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
