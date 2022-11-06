package br.com.dbc.vemser.sistemaaluguelveiculos.repository;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.EnderecoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {
    Page<EnderecoEntity> findByIdClienteLike(Integer id, Pageable pageable);
}
