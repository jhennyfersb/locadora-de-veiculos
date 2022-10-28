package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contato {
    private Integer idContato;
    private Integer idCliente;
    private String telefone;
    private String email;
}