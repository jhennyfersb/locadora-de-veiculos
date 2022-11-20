package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ClienteEntity;

import java.util.Collections;

public class ClienteFactory {
    public static ClienteEntity getClienteEntity(){
        return new ClienteEntity(2,
                "Jhennyfer",
                "05671239451",
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet());
    }
    public static ClienteDTO getClienteDTO(){
       return new ClienteDTO(getClienteEntity().getIdCliente());
    }
}
