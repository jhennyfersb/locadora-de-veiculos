package br.com.dbc.vemser.sistemaaluguelveiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "cupom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomDTO {

    private String id;

    private String email;

    private boolean ativo;

    private Double desconto;

    private LocalDate dataCriacao;

    private LocalDate dataVencimento;

}
