package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocacaoCreateDTO {

    @FutureOrPresent
    @Schema(description = "Data de locação.", example = "2022-12-20")
    private LocalDate dataLocacao;

    @Future
    @Schema(description = "Data de devolução.", example = "2022-12-26")
    private LocalDate dataDevolucao;

    @NotNull
    @Schema(description = "Id Cliente da locação.")
    private Integer idCliente;

    @NotNull
    @Schema(description = "Id Veículo da locação.")
    private Integer idVeiculo;

    @NotNull
    @Schema(description = "Id Cartão de crédito da locação.")
    private Integer idCartaoCredito;

    @NotNull
    @Schema(description = "Id Cartão da locação.")
    private Integer idFuncionario;
}
