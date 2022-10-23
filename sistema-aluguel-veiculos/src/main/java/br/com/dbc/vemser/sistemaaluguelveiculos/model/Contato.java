package br.com.dbc.vemser.sistemaaluguelveiculos.model;

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
    private String telefone;
    private String email;
}