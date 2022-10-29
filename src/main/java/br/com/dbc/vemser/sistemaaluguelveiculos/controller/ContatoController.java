package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.ContatoControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.ContatoService;
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
@RequestMapping("/contato")
public class ContatoController implements ContatoControllerInterface {

    private final ContatoService contatoService;

    @GetMapping
    public List<ContatoDTO> list() throws RegraDeNegocioException {
        return contatoService.list();
    }

    @GetMapping("/{idContato}")
    public ContatoDTO listByIdContato(@PathVariable("idContato") Integer id) throws RegraDeNegocioException {
        return contatoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ContatoDTO> create(@Valid @RequestBody ContatoCreateDTO contatoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando contato...");
        ContatoDTO contatoDTO = contatoService.create(contatoCreateDTO);
        log.info("contato criado");
        return new ResponseEntity<>(contatoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idContato}")
    public ResponseEntity<ContatoDTO> update(@PathVariable("idContato") Integer id,
                                                 @Valid @RequestBody ContatoCreateDTO contatoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando contato...");
        ContatoDTO contatoDTO = contatoService.update(id, contatoAtualizar);
        log.info("contato atualizado");
        return new ResponseEntity<>(contatoDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{idContato}")
    public ResponseEntity<ContatoDTO> delete(@PathVariable("idContato") Integer id) throws RegraDeNegocioException {
        log.info("Deletando contato...");
        contatoService.delete(id);
        log.info("contato deletado");
        return ResponseEntity.noContent().build();
    }
}
