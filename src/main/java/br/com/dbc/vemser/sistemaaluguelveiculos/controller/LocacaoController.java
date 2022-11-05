package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.LocacaoControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.LocacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/locacao")
public class LocacaoController implements LocacaoControllerInterface {

    private final LocacaoService locacaoService;

    @GetMapping
    public List<LocacaoDTO> list() throws RegraDeNegocioException {
        return locacaoService.list();
    }

    @GetMapping("/{idLocacao}")
    public LocacaoDTO listByIdLocacao(@PathVariable("idLocacao") Integer id) throws RegraDeNegocioException {
        return locacaoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<LocacaoDTO> create(@Valid @RequestBody LocacaoCreateDTO locacaoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando locação...");
        LocacaoDTO locacaoDTO = locacaoService.create(locacaoCreateDTO);
        log.info("Locação criada");
        return new ResponseEntity<>(locacaoDTO, HttpStatus.OK);
    }


    @PutMapping("/{idLocacao}")
    public ResponseEntity<LocacaoDTO> update(@PathVariable("idLocacao") Integer id,
                                             @Valid @RequestBody LocacaoCreateDTO locacaoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando locação...");
        LocacaoDTO locacaoDTO = locacaoService.update(id, locacaoAtualizar);
        log.info("Locação atualizado");
        return new ResponseEntity<>(locacaoDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{idLocacao}")
    public ResponseEntity<LocacaoDTO> delete(@PathVariable("idLocacao") Integer id) throws RegraDeNegocioException {
        log.info("Deletando locação...");
        locacaoService.delete(id);
        log.info("Locação deletada");
        return ResponseEntity.noContent().build();
    }
}
