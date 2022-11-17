package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.LogEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, String> {

    List<LogEntity> findAllByTipoLog(TipoLog tipoLog);

    Integer countByTipoLog(TipoLog tipoLog);

}
