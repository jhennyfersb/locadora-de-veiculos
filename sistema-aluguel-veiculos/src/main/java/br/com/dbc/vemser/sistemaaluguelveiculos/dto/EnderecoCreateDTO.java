package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoCreateDTO {

    @NotBlank(message = "Nome da rua não pode ser vazio ou nulo.")
    @Schema(description = "Rua do endereço", example="Rua Verde")
    private String rua;

    @NotNull
    @Schema(description = "Número da rua não pode ser vazio.", example="960")
    private String numero;

    @NotBlank(message = "Nome do bairro não pode ser vazio ou nulo")
    @Schema(description = "Bairro do endereço", example="Bela Vista")
    private String bairro;

    @NotBlank(message = "Nome da cidade não pode ser vazio ou nulo")
    @Schema(description = "Cidade do endereço.", example="Pato Branco")
    private String cidade;

    @NotBlank(message = "Nome do estado não pode ser vazio ou nulo")
    @Schema(description = "Estado do endereço", example="Paraná")
    private String estado;

    @NotBlank(message = "CEP não pode ser vazio ou nulo")
    @Schema(description = "CEP do endereço", example="15505-903")
    private String cep;

    @Schema(description = "Complemento do endereço", example="Ap 13")
    private String complemento;
}
