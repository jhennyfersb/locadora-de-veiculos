package br.com.dbc.vemser.sistemaaluguelveiculos.repository.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<CHAVE, OBJETO> {
    Integer getProximoId(Connection connection) throws SQLException;

    OBJETO create(OBJETO object) throws BancoDeDadosException;

    OBJETO update(CHAVE id, OBJETO objeto) throws BancoDeDadosException;

    boolean delete(CHAVE id) throws BancoDeDadosException;

    List<OBJETO> list() throws BancoDeDadosException;
}
