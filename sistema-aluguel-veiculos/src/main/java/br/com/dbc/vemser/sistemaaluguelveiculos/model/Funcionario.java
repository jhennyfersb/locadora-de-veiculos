package br.com.dbc.vemser.sistemaaluguelveiculos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Funcionario {
    private Integer idFuncionario;
    private Integer matricula;
    private String nome;
    private String cpf;
}
