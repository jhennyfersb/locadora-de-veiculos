package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteCreateDTO {

    @NotBlank
    @Schema(description = "Nome do cliente.", example = "Carlinhos da viola")
    private String nome;

    @NotBlank(message = "Cpf não pode ser vazio ou nulo")
    @Size(min = 11, max = 11)
    @Schema(description = "Cpf do cliente.", example = "84858485844")
    private String cpf;
}
