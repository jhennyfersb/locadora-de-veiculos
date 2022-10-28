package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.CartaoCreditoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cartao")
public class CartaoCreditoController implements CartaoCreditoControllerInterface{

    private final CartaoCreditoService cartaoCreditoService;

    @GetMapping
    public List<CartaoCreditoDTO> list() {
        return cartaoCreditoService.list();
    }

    @PostMapping
    public ResponseEntity<CartaoCreditoDTO> create(@Valid @RequestBody CartaoCreditoCreateDTO cartaoCredito) {
        log.info("Criando Cartão de Crédito...");
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.create(cartaoCredito);
        log.info("Cartão de Crédto criado!");
        return new ResponseEntity<>(cartaoCreditoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{idCartao}")
    public ResponseEntity<CartaoCreditoDTO> update(@PathVariable ("idCartao") Integer idCartao,
                                                   @Valid @RequestBody CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException {
        log.info("Atualizando Cartão de Crédito...");
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.update(idCartao, cartaoCredito);
        log.info("Cartão de Crédito Atualizado!");
        return new ResponseEntity<>(cartaoCreditoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idCartao}")
    public ResponseEntity<Void> delete(@PathVariable ("idCartao") Integer idCartao) throws RegraDeNegocioException {
        log.info("Deletando Cartão de Crédito...");
        cartaoCreditoService.delete(idCartao);
        log.info("Cartão de Crédito deletado!");
        return ResponseEntity.noContent().build();
    }
}
