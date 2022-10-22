package br.com.dbc.vemser.sistemaaluguelveiculos.model;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.DatasInvalidasException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartaoCredito {
    private int idCartaoCredito;
    private String numero;
    private BandeiraCartao bandeira;
    private String validade;
    private double limite;


//    public static void validarDataValidadeCartao(String validade) throws DatasInvalidasException {
//        if(Integer.parseInt(validade.substring(0,2)) > 12){
//            throw new DatasInvalidasException("Mês inválido. Tente novamente!");
//        }else if(Integer.parseInt(validade.substring(3)) < LocalDate.now().getYear()){
//            throw new DatasInvalidasException("Cartão inválido, Data de vencimento do cartão inferior ao ano atual. Tente outro cartão!");
//        }else if(Integer.parseInt(validade.substring(3)) == LocalDate.now().getYear()) {
//            if(Integer.parseInt(validade.substring(0,2)) <= LocalDate.now().getMonthValue()) {
//                throw new DatasInvalidasException("Cartão inválido, Data de vencimento do cartão inferior ao ano atual. Tente outro cartão!");
//            }
//        }
//    }
}