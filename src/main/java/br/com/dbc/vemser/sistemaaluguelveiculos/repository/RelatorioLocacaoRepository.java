package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioLocacaoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioLocacaoPorClienteDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioLocacaoRepository extends MongoRepository<RelatorioLocacaoDTO, String> {
//    @Aggregation(pipeline = {
//            "{ '$unwind' : '$cidade' }",
//            "{'$group':{ '_id': '$tipoLog', 'quantidade' : {'$sum': 1} }}"
//    })
//    List<RelatorioLocacaoPorClienteDTO> relatorioLocacaoPorCidade();

//    @Aggregation(pipeline = {
//            "{ $match: {} }",
//            "{ $group: { _id: "", sumQuantity: {$sum: "$valorLocacao" }} }"
//    })
//    List<RelatorioLocacaoDTO> find
}
