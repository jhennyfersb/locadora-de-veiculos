package br.com.dbc.vemser.sistemaaluguelveiculos.exceptions;

public class InvalidTokenException extends RegraDeNegocioException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
