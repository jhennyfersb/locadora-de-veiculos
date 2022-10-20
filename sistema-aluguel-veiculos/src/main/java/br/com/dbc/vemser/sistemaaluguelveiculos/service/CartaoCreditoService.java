package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.CartaoCredito;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.CartaoCreditoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoCreditoService {
    private CartaoCreditoRepository cartaoCreditoRepository;

    public CartaoCreditoService(CartaoCreditoRepository cartaoCreditoRepository) {
        this.cartaoCreditoRepository = cartaoCreditoRepository;
    }


    public void adicionarCartao(CartaoCredito cartaoCredito) {
        try {
            CartaoCredito cartaoAdicionado = cartaoCreditoRepository.adicionar(cartaoCredito);
            //System.out.println("cartão adicinado com sucesso! " + cartaoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public void removerCartao(Integer id) {
        try {
            boolean conseguiuRemover = cartaoCreditoRepository.remover(id);
            //System.out.println("cartão removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void editarCartao(Integer id, CartaoCredito cartaoCredito) {
        try {
            boolean conseguiuEditar = cartaoCreditoRepository.editar(id, cartaoCredito);
            //System.out.println("cartão editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listarCartoes() {
        try {
            List<CartaoCredito> listar = cartaoCreditoRepository.listar();
            listar.forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }
}
