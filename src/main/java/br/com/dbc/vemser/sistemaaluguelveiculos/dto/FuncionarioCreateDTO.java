package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioCreateDTO {

    @NotNull
    @Schema(description = "Nome do funcionário.", example = "Maifa")
    private String nome;

    @NotNull
    @Size(min = 11, max = 11)
    @Schema(description = "Cpf do funcionário.", example = "99999999999")
    private String cpf;

    @NotEmpty
    @NotNull
    @Schema(description = "E-mail do funcionário", example = "maifa@javamail.com.br")
    private String email;

    @NotNull
    @Schema(description = "Número de matrícula do funcionário.", example = "8")
    private Integer matricula;

    @NotNull
    @Schema(description = "senha do funcionário.", example = "123")
    private String senha;

    @NotNull
    @Schema(description = "id do cargo do funcionário.", example = "admin = 1, auxiliar = 2 ,cliente = 3")
    private Integer idCargo;


}
