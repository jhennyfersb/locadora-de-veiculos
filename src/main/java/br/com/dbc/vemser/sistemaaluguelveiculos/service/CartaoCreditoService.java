package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCreditoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.CartaoCreditoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartaoCreditoService {
    private final CartaoCreditoRepository cartaoCreditoRepository;
    private final ObjectMapper objectMapper;

    public CartaoCreditoDTO create(CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException {
        CartaoCreditoEntity cartaoCreditoEntity = converterEntity(cartaoCredito);
        return converterEmDTO(cartaoCreditoRepository.save(cartaoCreditoEntity));

    }

    public CartaoCreditoDTO update(Integer idCartao, CartaoCreditoCreateDTO cartaoCreditoAtualizar) throws RegraDeNegocioException {
        this.findById(idCartao);
        CartaoCreditoEntity ccEntity = converterEntity(cartaoCreditoAtualizar);
        ccEntity.setIdCartaoCredito(idCartao);
        return converterEmDTO(cartaoCreditoRepository.save(ccEntity));

    }

    public void delete(Integer idCartao) throws RegraDeNegocioException {
        this.findById(idCartao);
        cartaoCreditoRepository.deleteById(idCartao);
    }

    public List<CartaoCreditoDTO> list() throws RegraDeNegocioException {
        return cartaoCreditoRepository.findAll().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

    }

    public CartaoCreditoEntity converterEntity(CartaoCreditoCreateDTO cartaoCreditoCreateDTO) {
        return objectMapper.convertValue(cartaoCreditoCreateDTO, CartaoCreditoEntity.class);
    }

    public CartaoCreditoDTO converterEmDTO(CartaoCreditoEntity cartaoCreditoEntity) {
        return objectMapper.convertValue(cartaoCreditoEntity, CartaoCreditoDTO.class);
    }

    public CartaoCreditoDTO findById(Integer id) throws RegraDeNegocioException {

        Optional<CartaoCreditoEntity> ccRecuperado = cartaoCreditoRepository.findById(id);

        if (ccRecuperado == null) {
            throw new RegraDeNegocioException("Cartão de Crédito não encontrado");
        }
        return objectMapper.convertValue(ccRecuperado, CartaoCreditoDTO.class);

    }
}
