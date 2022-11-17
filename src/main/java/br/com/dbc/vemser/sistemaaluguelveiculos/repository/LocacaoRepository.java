package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioLocacaoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioLocacaoPorCidadeDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioLocacaoPorClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.LocacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LocacaoRepository extends JpaRepository<LocacaoEntity, Integer> {

    @Query("select new br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioLocacaoPorClienteDTO(" +
            "cl.idCliente," +
            "cl.nome," +
            "cl.cpf," +
            "count(cl.idCliente)) " +
            "from  locacao l " +
            "left join  cliente cl on cl.idCliente= l.clienteEntity.idCliente " +
            "group by cl.idCliente,cl.nome,cl.cpf order by count(cl.idCliente) desc ")
    List<RelatorioLocacaoPorClienteDTO> locacaoPorClienteQuantidade();

    @Query("select new br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioLocacaoPorCidadeDTO(" +
            "(select distinct EC.cidade from endereco_cliente EC where l.clienteEntity.idCliente = EC.idCliente)," +
            "count(l.clienteEntity.idCliente)) " +
            "from  locacao l " +
            "group by l.clienteEntity.idCliente order by count(l.clienteEntity.idCliente) desc ")
    List<RelatorioLocacaoPorCidadeDTO> locacaoPorCidade();

}
