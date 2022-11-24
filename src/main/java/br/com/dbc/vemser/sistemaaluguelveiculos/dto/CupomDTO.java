package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomDTO {
    private Integer idCupom;
    private LocalDate dataEmissao;
    private LocalDate dataExpiracao;
    private Double porcentagem;
    private Integer qtLimiteCupom;
}
