package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Contato;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteCreateDTO {
    private Contato contato;
    private Endereco endereco;
    private String nome;
    private String cpf;
}
