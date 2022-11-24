package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelatorioLocacaoPorClienteDTO {
    private Integer idCliente;
    private String nome;
    private String cpf;
    private long quantidadeLocacao;

}
