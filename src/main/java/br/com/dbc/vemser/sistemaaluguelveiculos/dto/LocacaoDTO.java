package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LocacaoDTO  {
    private Integer idLocacao;
    private LocalDate dataLocacao;
    private Double valorLocacao;
    private Cliente cliente;
    private Veiculo veiculo;
    private CartaoCredito cartaoCredito;
    private Funcionario funcionario;
}
