package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotNull
    @Schema(description = "Cargo do usuario")
    private String cargoNome;
    @NotNull
    @Schema(description = "Cpf do usuário", example = "85235795125")
    private String cpf;
    @NotNull
    @Schema(description = "Nome do funcionário.", example = "Maifa")
    private String nome;
    @Schema(description = "E-mail do funcionário", example = "maifa@javamail.com.br")
    private String email;


}
