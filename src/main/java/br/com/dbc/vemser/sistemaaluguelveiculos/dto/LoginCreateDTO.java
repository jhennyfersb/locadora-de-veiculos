package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCreateDTO {
    @NotNull
    @Schema(description = "Cpf do usuário", example="85235795125")
    private String cpf;
    @NotNull
    @Schema(description = "Senha do usuário", example="123")
    private String senha;


}
