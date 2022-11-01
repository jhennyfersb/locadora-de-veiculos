package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LocacaoDTO  {
    private Integer idLocacao;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private Double valorLocacao;
    private ClienteDTO cliente;
    private VeiculoDTO veiculo;
    private CartaoCreditoDTO cartaoCredito;
    private FuncionarioDTO funcionario;
}
