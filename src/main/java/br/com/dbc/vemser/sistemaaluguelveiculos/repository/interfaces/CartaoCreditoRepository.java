package br.com.dbc.vemser.sistemaaluguelveiculos.repository.interfaces;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCreditoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCreditoEntity,Integer> {
}
