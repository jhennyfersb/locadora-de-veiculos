package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LocacaoDTO {
    private Integer idLocacao;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private Double valorLocacao;
    private ClienteDTO clienteEntity;
    private VeiculoDTO veiculoEntity;
    private CartaoCreditoDTO cartaoCreditoEntity;
    private FuncionarioDTO funcionarioEntity;
}
