package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.CartaoCredito;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.CartaoCreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoCreditoService {
    private final CartaoCreditoRepository cartaoCreditoRepository;

    public void create(CartaoCredito cartaoCredito) {
        try {
            CartaoCredito cartaoAdicionado = cartaoCreditoRepository.create(cartaoCredito);
            //System.out.println("cartão adicinado com sucesso! " + cartaoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        try {
            boolean conseguiuRemover = cartaoCreditoRepository.delete(id);
            //System.out.println("cartão removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, CartaoCredito cartaoCredito) {
        try {
            boolean conseguiuEditar = cartaoCreditoRepository.update(id, cartaoCredito);
            //System.out.println("cartão editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void list() {
        try {
            List<CartaoCredito> listar = cartaoCreditoRepository.list();
            listar.forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }
}
