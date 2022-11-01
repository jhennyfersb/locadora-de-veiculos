package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCreditoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.CartaoCreditoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartaoCreditoService {
    private final CartaoCreditoRepository cartaoCreditoRepository;
    private final ObjectMapper objectMapper;

    public CartaoCreditoDTO create(CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException {
        try {
            CartaoCreditoEntity cartaoCreditoEntity = converterEntity(cartaoCredito);
            return converterEmDTO(cartaoCreditoRepository.save(cartaoCreditoEntity));
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public CartaoCreditoDTO update(Integer idCartao, CartaoCreditoCreateDTO cartaoCreditoAtualizar) throws RegraDeNegocioException {
        try {
            this.findById(idCartao);
            CartaoCreditoEntity ccEntity = converterEntity(cartaoCreditoAtualizar);
            ccEntity.setIdCartaoCredito(idCartao);
            return converterEmDTO(cartaoCreditoRepository.save(ccEntity));
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao atualizar no banco de dados.");
        }
    }

    public void delete(Integer idCartao) throws RegraDeNegocioException {
        try {
            this.findById(idCartao);
            cartaoCreditoRepository.deleteById(idCartao);
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public List<CartaoCreditoDTO> list() throws RegraDeNegocioException {
        try {
            return cartaoCreditoRepository.findAll().stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao recuperar os dados no banco de dados.");
        }
    }

    public CartaoCreditoEntity converterEntity(CartaoCreditoCreateDTO cartaoCreditoCreateDTO) {
        return objectMapper.convertValue(cartaoCreditoCreateDTO, CartaoCreditoEntity.class);
    }

    public CartaoCreditoDTO converterEmDTO(CartaoCreditoEntity cartaoCreditoEntity) {
        return objectMapper.convertValue(cartaoCreditoEntity, CartaoCreditoDTO.class);
    }

    public CartaoCreditoDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            Optional<CartaoCreditoEntity> ccRecuperado = cartaoCreditoRepository.findById(id);

            if (ccRecuperado == null) {
                throw new RegraDeNegocioException("Cartão de Crédito não encontrado");
            }
            return objectMapper.convertValue(ccRecuperado,CartaoCreditoDTO.class);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao procurar no banco de dados.");
        }
    }
}
