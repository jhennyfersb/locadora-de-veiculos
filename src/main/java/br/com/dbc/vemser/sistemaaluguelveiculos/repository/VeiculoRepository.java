package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.VeiculoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Integer> {
    @Query("select v from veiculo v where v.disponibilidadeVeiculo = :disponibilidadeVeiculo ")
    List<VeiculoEntity> retornarVeiculosPorDisponibilidade(@Param("disponibilidadeVeiculo") DisponibilidadeVeiculo disponibilidadeVeiculo);


}
