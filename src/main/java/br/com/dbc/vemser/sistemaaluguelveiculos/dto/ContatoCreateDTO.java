package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoCreateDTO {

    @NotBlank(message = "Nome não pode ser vazio ou nulo")
    @Schema(description = "Telefone do contato", example="99595-1313")
    private String telefone;

    @Email
    @Schema(description = "Email do contato", example="bruno.bardu@dbccompany.com.br")
    private String email;
}
