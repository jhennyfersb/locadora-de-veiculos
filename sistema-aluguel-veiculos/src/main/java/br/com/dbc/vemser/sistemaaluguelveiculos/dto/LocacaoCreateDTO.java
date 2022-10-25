package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCredito;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Cliente;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Veiculo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocacaoCreateDTO {


    @FutureOrPresent
    @Schema(description = "Data de locação.", example = "20/12/2022")
    private LocalDate dataLocacao;

    @Future
    @Schema(description = "Data de devolução.", example = "26/12/2022")
    private LocalDate dataDevolucao;

    @Min(1)
    @Schema(description = "Data de devolução.", example = "26/12/2022")
    private Double valorLocacao;

    @NotNull
    @Schema(description = "Cliente da locação.")
    private Cliente cliente;

    @NotNull
    @Schema(description = "Veículo da locação.")
    private Veiculo veiculo;

    @NotNull
    @Schema(description = "Cartão de crédito da locação.")
    private CartaoCredito cartaoCredito;

    @NotNull
    @Schema(description = "Funcionário da locação.")
    private Funcionario funcionario;
}
