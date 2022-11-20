package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CartaoCreditoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.CartaoCreditoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartaoCreditoService {
    private final CartaoCreditoRepository cartaoCreditoRepository;
    private final ObjectMapper objectMapper;
    private final LogService logService;

    public CartaoCreditoDTO create(CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException {
        CartaoCreditoEntity cartaoCreditoEntity = converterEntity(cartaoCredito);
        CartaoCreditoDTO cartaoCreditoDTO = converterEmDTO(cartaoCreditoRepository.save(cartaoCreditoEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.CREATE, "CPF logado: " + cpf, EntityLog.CARTAO_CREDITO));
        return cartaoCreditoDTO;
    }

    public CartaoCreditoDTO update(Integer idCartao, CartaoCreditoCreateDTO cartaoCreditoAtualizar) throws RegraDeNegocioException {
        this.findById(idCartao);
        CartaoCreditoEntity ccEntity = converterEntity(cartaoCreditoAtualizar);
        ccEntity.setIdCartaoCredito(idCartao);
        CartaoCreditoDTO cartaoCreditoDTO = converterEmDTO(cartaoCreditoRepository.save(ccEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE, "CPF logado: " + cpf, EntityLog.CARTAO_CREDITO));
        return cartaoCreditoDTO;

    }

    public void delete(Integer idCartao) throws RegraDeNegocioException {
        this.findById(idCartao);
        cartaoCreditoRepository.deleteById(idCartao);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.DELETE, "CPF logado: " + cpf, EntityLog.CARTAO_CREDITO));
    }

    public List<CartaoCreditoDTO> list() throws RegraDeNegocioException {
        List<CartaoCreditoDTO> lista = cartaoCreditoRepository.findAll().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.CARTAO_CREDITO));
        return lista;
    }

    private CartaoCreditoEntity converterEntity(CartaoCreditoCreateDTO cartaoCreditoCreateDTO) {
        return objectMapper.convertValue(cartaoCreditoCreateDTO, CartaoCreditoEntity.class);
    }

    private CartaoCreditoDTO converterEmDTO(CartaoCreditoEntity cartaoCreditoEntity) {
        return objectMapper.convertValue(cartaoCreditoEntity, CartaoCreditoDTO.class);
    }

    public CartaoCreditoDTO findDtoById(Integer id) throws RegraDeNegocioException {

        CartaoCreditoEntity ccRecuperado = findById(id);

        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ, "CPF logado: " + cpf, EntityLog.CARTAO_CREDITO));

        return objectMapper.convertValue(ccRecuperado, CartaoCreditoDTO.class);

    }

    private CartaoCreditoEntity findById(Integer id) throws RegraDeNegocioException {
        return cartaoCreditoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cartão não encontrado."));
    }

}
