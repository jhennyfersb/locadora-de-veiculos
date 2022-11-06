package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.CartaoCreditoControllerInterface;
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
public class CartaoCreditoController implements CartaoCreditoControllerInterface {

    private final CartaoCreditoService cartaoCreditoService;

    @GetMapping
    public List<CartaoCreditoDTO> list() throws RegraDeNegocioException {
        return cartaoCreditoService.list();
    }

    @GetMapping("/{idCartaoCredito}")
    public CartaoCreditoDTO listByIdCartaoCredito(@PathVariable("idCartaoCredito") Integer id) throws RegraDeNegocioException {
        return cartaoCreditoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CartaoCreditoDTO> create(@Valid @RequestBody CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException {
        log.info("Criando Cartão de crédito...");
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.create(cartaoCredito);
        log.info("Cartão de crédito criado!");
        return new ResponseEntity<>(cartaoCreditoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{idCartao}")
    public ResponseEntity<CartaoCreditoDTO> update(@PathVariable("idCartao") Integer idCartao,
                                                   @Valid @RequestBody CartaoCreditoCreateDTO cartaoCredito) throws RegraDeNegocioException {
        log.info("Atualizando Cartão de crédito...");
        CartaoCreditoDTO cartaoCreditoDTO = cartaoCreditoService.update(idCartao, cartaoCredito);
        log.info("Cartão de crédito Atualizado!");
        return new ResponseEntity<>(cartaoCreditoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idCartao}")
    public ResponseEntity<Void> delete(@PathVariable("idCartao") Integer idCartao) throws RegraDeNegocioException {
        log.info("Deletando Cartão de crédito...");
        cartaoCreditoService.delete(idCartao);
        log.info("Cartão de crédito deletado!");
        return ResponseEntity.noContent().build();
    }
}
