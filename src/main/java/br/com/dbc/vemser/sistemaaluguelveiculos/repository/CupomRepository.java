package br.com.dbc.vemser.sistemaaluguelveiculos.repository;


import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CupomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends MongoRepository<CupomEntity, String> {
}
