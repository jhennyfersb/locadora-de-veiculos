package br.com.dbc.vemser.sistemaaluguelveiculos.model;

public enum BandeiraCartao {

    VISA(1),
    MASTERCARD(2);

    private int tipoBandeira;

    BandeiraCartao(int tipoBandeira){
        this.tipoBandeira = tipoBandeira;
    }

    public int getTipoBandeira() {
        return tipoBandeira;
    }
}
