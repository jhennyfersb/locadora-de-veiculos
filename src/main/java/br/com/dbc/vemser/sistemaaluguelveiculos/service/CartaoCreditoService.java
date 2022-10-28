package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCredito;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.CartaoCreditoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartaoCreditoService {
    private final CartaoCreditoRepository cartaoCreditoRepository;
    private final ObjectMapper objectMapper;

    public CartaoCreditoDTO create(CartaoCreditoCreateDTO cartaoCredito) {
        try {
            CartaoCredito cartaoCreditoEntity = objectMapper.convertValue(cartaoCredito, CartaoCredito.class);
            CartaoCreditoDTO cartaoCreditoDTO= objectMapper.convertValue(cartaoCreditoRepository.create(cartaoCreditoEntity), CartaoCreditoDTO.class);
            return cartaoCreditoDTO;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CartaoCreditoDTO update(Integer idCartao, CartaoCreditoCreateDTO cartaoCreditoAtualizar) throws RegraDeNegocioException {
        try {
            CartaoCredito cartaoRecuperado = cartaoCreditoRepository.getPorId(idCartao);

            if(cartaoRecuperado.getIdCartaoCredito() != null) {
                CartaoCredito cartaoCreditoEntity = objectMapper.convertValue(cartaoCreditoAtualizar, CartaoCredito.class);
                CartaoCreditoDTO cartaoCreditoDTO = objectMapper.convertValue(cartaoCreditoRepository.update(idCartao, cartaoCreditoEntity), CartaoCreditoDTO.class);
                return cartaoCreditoDTO;
            }else {
                throw new RegraDeNegocioException("Cartão de Crédito não encontrado!");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer idCartao) throws RegraDeNegocioException {
        try {
            CartaoCredito cartaoRecuperado = cartaoCreditoRepository.getPorId(idCartao);
            if(cartaoRecuperado.getIdCartaoCredito() != null) {
                cartaoCreditoRepository.delete(idCartao);
            }else {
                throw new RegraDeNegocioException("Cartão de Crédito não encontrado!");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public List<CartaoCreditoDTO> list() {
        try {
            return cartaoCreditoRepository.list().stream()
                    .map(cartao -> objectMapper.convertValue(cartao, CartaoCreditoDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CartaoCredito findById(Integer idCartaoCredito) throws BancoDeDadosException {
        return cartaoCreditoRepository.getPorId(idCartaoCredito);
    }
}
