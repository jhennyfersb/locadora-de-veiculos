package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioCreateDTO{
    @NotNull
    private Integer matricula;
    @NotNull
    private String nome;
    @Size(min = 11, max = 11)
    private String cpf;
}
