package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class CupomCreateDTO {


    @Email
    private String email;

    @NotNull
    private boolean ativo;

    @NotNull
    private Double desconto;

    @Past
    @NotNull
    private LocalDate dataCriacao;

    @PastOrPresent
    @NotNull
    private LocalDate dataVencimento;
}
