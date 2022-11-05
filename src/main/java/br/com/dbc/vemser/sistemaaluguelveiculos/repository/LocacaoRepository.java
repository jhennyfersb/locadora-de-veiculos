package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.LocacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface LocacaoRepository extends JpaRepository<LocacaoEntity,Integer> {

    @Query(" select new br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioDTO(" +
            " c.nome," +
            " c.cpf," +
            " co.telefone," +
            " co.email," +
            " e.cidade," +
            " l.dataLocacao," +
            " l.dataDevolucao," +
            " l.valorLocacao," +
            " v.marca," +
            " v.modelo," +
            " v.cor," +
            " v.ano," +
            " v.placa," +
            " v.quilometragem," +
            " f.nome)" +
            "  from locacao l" +
            " left join l.clienteEntity c" +
            " left join c.contatoEntities co" +
            " left join c.enderecoEntities e" +
            " left join l.veiculoEntity v" +
            " left join l.funcionarioEntity f" +
            " where (:idCliente is null or c.idCliente = :idCliente)"+
            " and (:idVeiculo is null or v.idVeiculo = :idVeiculo)"+
            " and (:idFuncionario is null or f.idFuncionario = :idFuncionario)")
    List<RelatorioDTO> listarRelatoriosLocacao(Integer idCliente, Integer idVeiculo,Integer idFuncionario);


}
