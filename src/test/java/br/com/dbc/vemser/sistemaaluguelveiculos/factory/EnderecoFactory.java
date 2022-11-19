package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.EnderecoEntity;

public class EnderecoFactory {
    public static EnderecoEntity getEnderecoEntity() {
        return new EnderecoEntity(1,
                2,
                "Av Brasil",
                "746",
                "Centro",
                "Pato Branco",
                "Paraná",
                "85501057",
                "perto de",ClienteFactory.getCliente());
    }
    public static EnderecoCreateDTO getEnderecoCreateDTO() {
        EnderecoCreateDTO enderecoCreateDTO = new EnderecoCreateDTO(1,
                "Av Brasil",
                "746",
                "Centro",
                "Pato Branco",
                "Paraná",
                "85501057",
                "perto de");
        return enderecoCreateDTO;
    }
}
