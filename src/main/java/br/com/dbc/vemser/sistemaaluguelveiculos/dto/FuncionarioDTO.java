package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {
    private Integer idFuncionario;

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

}
