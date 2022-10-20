package br.com.dbc.vemser.sistemaaluguelveiculos.model;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.DatasInvalidasException;

import java.time.LocalDate;

public class CartaoCredito {
    private int idCartaoCredito;
    private String numero;
    private BandeiraCartao bandeira;
    private String validade;
    private double limite;

    public CartaoCredito(){
    }

    public CartaoCredito(String numero, BandeiraCartao bandeira, String validade, double limite){
        this.numero = numero;
        this.bandeira = bandeira;
        this.validade = validade;
        this.limite = limite;
    }
    public CartaoCredito(int idCartaoCredito) {
        this.idCartaoCredito = idCartaoCredito;
    }

    public int getIdCartaoCredito() {
        return idCartaoCredito;
    }

    public void setIdCartaoCredito(int idCartaoCredito) {
        this.idCartaoCredito = idCartaoCredito;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BandeiraCartao getBandeira() {
        return bandeira;
    }

    public void setBandeira(BandeiraCartao tipoBandeira){
        bandeira = tipoBandeira;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public static void validarDataValidadeCartao(String validade) throws DatasInvalidasException {
        if(Integer.parseInt(validade.substring(0,2)) > 12){
            throw new DatasInvalidasException("Mês inválido. Tente novamente!");
        }else if(Integer.parseInt(validade.substring(3)) < LocalDate.now().getYear()){
            throw new DatasInvalidasException("Cartão inválido, Data de vencimento do cartão inferior ao ano atual. Tente outro cartão!");
        }else if(Integer.parseInt(validade.substring(3)) == LocalDate.now().getYear()) {
            if(Integer.parseInt(validade.substring(0,2)) <= LocalDate.now().getMonthValue()) {
                throw new DatasInvalidasException("Cartão inválido, Data de vencimento do cartão inferior ao ano atual. Tente outro cartão!");
            }
        }
    }

    @Override
    public String toString() {
        return "CartaoCredito{" +
                "idCartaoCredito=" + idCartaoCredito +
                ", numero='" + numero + '\'' +
                ", bandeira=" + bandeira +
                ", validade='" + validade + '\'' +
                ", limite=" + limite +
                '}';
    }
}