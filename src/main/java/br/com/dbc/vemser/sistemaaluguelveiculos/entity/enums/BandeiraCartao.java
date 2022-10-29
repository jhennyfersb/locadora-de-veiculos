package br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums;

public enum BandeiraCartao {
    VISA(1),
    MASTERCARD(2);

    private Integer tipoBandeira;

    BandeiraCartao(Integer tipoBandeira){
        this.tipoBandeira = tipoBandeira;
    }

    public Integer getTipoBandeira() {
        return tipoBandeira;
    }
}
