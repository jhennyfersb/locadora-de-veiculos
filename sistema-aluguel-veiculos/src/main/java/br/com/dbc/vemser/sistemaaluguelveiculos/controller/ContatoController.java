package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ContatoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
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
@RequestMapping("/contato") // localhost:8080/contato
public class ContatoController implements ContatoControllerInterface {

    private final ContatoService contatoService;

    @GetMapping // localhost:8080/contato OK
    public List<ContatoDTO> list() throws BancoDeDadosException {
        return contatoService.list();
    }

//    @GetMapping("/{idFuncionario}") // localhost:8080/endereco/2 OK
//    public List<FuncionarioDTO> listByIdFuncionario(@PathVariable("idFuncionario") Integer id) {
//        return funcionarioService.listByIdEndereco(id);
//    }

    @PostMapping // localhost:8080/contato/4 OK
    public ResponseEntity<ContatoDTO> create(@Valid @RequestBody ContatoCreateDTO contatoCreateDTO) throws Exception {
        log.info("Criando contato...");
        ContatoDTO contatoDTO = contatoService.create(contatoCreateDTO);
        log.info("contato criado");
        return new ResponseEntity<>(contatoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idContato}") // localhost:8080/contato/1000 OK
    public ResponseEntity<ContatoDTO> update(@PathVariable("idContato") Integer id,
                                                 @Valid @RequestBody ContatoCreateDTO contatoAtualizar) throws Exception {
        log.info("Atualizando contato...");
        ContatoDTO contatoDTO = contatoService.update(id, contatoAtualizar);
        log.info("contato atualizado");
        return new ResponseEntity<>(contatoDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{idContato}") // localhost:8080/contato/10 OK
    public ResponseEntity<ContatoDTO> delete(@PathVariable("idContato") Integer id) throws Exception {
        log.info("Deletando contato...");
        contatoService.delete(id);
        log.info("contato deletado");
        return ResponseEntity.noContent().build();
    }
}
