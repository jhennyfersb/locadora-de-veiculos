package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCredito;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
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

    public CartaoCreditoDTO create(CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException {
        try {
            CartaoCredito cartaoCreditoEntity = converterEntity(cartaoCredito);
            return converterEmDTO(cartaoCreditoRepository.create(cartaoCreditoEntity));
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public CartaoCreditoDTO update(Integer idCartao, CartaoCreditoCreateDTO cartaoCreditoAtualizar) throws RegraDeNegocioException {
        try {
            CartaoCredito cartaoRecuperado = cartaoCreditoRepository.findById(idCartao);

            if(cartaoRecuperado.getIdCartaoCredito() != null) {
                CartaoCredito cartaoCreditoEntity = converterEntity(cartaoCreditoAtualizar);
                return converterEmDTO(cartaoCreditoRepository.update(idCartao, cartaoCreditoEntity));
            }else {
                throw new RegraDeNegocioException("Cartão de Crédito não encontrado!");
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
    }

    public void delete(Integer idCartao) throws RegraDeNegocioException {
        try {
            CartaoCredito cartaoRecuperado = cartaoCreditoRepository.findById(idCartao);
            if(cartaoRecuperado.getIdCartaoCredito() != null) {
                cartaoCreditoRepository.delete(idCartao);
            }else {
                throw new RegraDeNegocioException("Cartão de Crédito não encontrado!");
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public List<CartaoCreditoDTO> list() throws RegraDeNegocioException {
        try {
            return cartaoCreditoRepository.list().stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao recuperar os dados no banco de dados.");
        }
    }

    public CartaoCredito converterEntity(CartaoCreditoCreateDTO cartaoCreditoCreateDTO){
        return objectMapper.convertValue(cartaoCreditoCreateDTO, CartaoCredito.class);
    }

    public CartaoCreditoDTO converterEmDTO(CartaoCredito cartaoCredito){
        return objectMapper.convertValue(cartaoCredito, CartaoCreditoDTO.class);
    }

    public CartaoCreditoDTO findById(Integer id) throws RegraDeNegocioException{
        try {
            return converterEmDTO(cartaoCreditoRepository.findById(id));
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao procurar no banco de dados.");
        }
    }
}
