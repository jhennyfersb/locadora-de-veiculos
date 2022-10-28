package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.*;
import lombok.Data;

@Data
public class LocacaoDTO {
    private Integer idLocacao;

    private Cliente cliente;
    private Veiculo veiculo;
    private CartaoCredito cartaoCredito;
    private Funcionario funcionario;

}
