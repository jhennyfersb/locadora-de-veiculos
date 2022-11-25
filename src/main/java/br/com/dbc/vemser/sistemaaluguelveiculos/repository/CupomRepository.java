package br.com.dbc.vemser.sistemaaluguelveiculos.repository;


import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioCupom;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CupomEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CupomRepository extends MongoRepository<CupomEntity, String> {
    @Aggregation(pipeline = {
            "{ '$unwind' : '$ativo' }",
            "{'$group':{ '_id': '$ativo', 'quantidade' : {'$sum': 1} }}"
    })
    List<RelatorioCupom> relatorioCupomStatus();
}
