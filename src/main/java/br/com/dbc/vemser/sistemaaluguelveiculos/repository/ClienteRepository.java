package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    @Query("select new br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioClienteDTO(" +
            "cl.idCliente," +
            "cl.nome," +
            "cl.cpf," +
            "c.telefone," +
            "c.email," +
            "e.rua," +
            "e.numero," +
            "e.bairro," +
            "e.cidade," +
            "e.estado," +
            "e.cep," +
            "e.complemento," +
            "l.cartaoCreditoEntity.numero," +
            "l.cartaoCreditoEntity.bandeiraCartao," +
            "l.cartaoCreditoEntity.validade," +
            "l.cartaoCreditoEntity.limite)" +
            "from cliente cl " +
            "left join cl.contatoEntities c " +
            "left join cl.enderecoEntities e " +
            "left join cl.locacaoEntities l " +
            " where (:idCliente is null or c.idCliente = :idCliente)")
    List<RelatorioClienteDTO> relatorioCliente(Integer idCliente);

}
