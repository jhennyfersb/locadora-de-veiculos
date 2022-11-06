package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.BandeiraCartao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelatorioClienteDTO {
    private Integer idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
    private String numeroCartao;
    private BandeiraCartao bandeiraCartao;
    private String validade;
    private Double limite;
}
