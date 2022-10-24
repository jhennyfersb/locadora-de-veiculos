package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.EnderecoService;
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
@RequestMapping("/endereco") // localhost:8080/endereco
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping // localhost:8080/enderecp OK
    public List<EnderecoDTO> list() throws BancoDeDadosException {
        return enderecoService.list();
    }

    @PostMapping // localhost:8080/endereco/4 OK
    public ResponseEntity<EnderecoDTO> create(@Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws Exception {
        log.info("Criando endereco...");
        EnderecoDTO enderecoDTO = enderecoService.create(enderecoCreateDTO);
        log.info("endereco criado");
        return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idEndereco}") // localhost:8080/endereco/1000 OK
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id,
                                             @Valid @RequestBody EnderecoCreateDTO enderecoAtualizar) throws Exception {
        log.info("Atualizando endereco...");
        EnderecoDTO enderecoDTO = enderecoService.update(id, enderecoAtualizar);
        log.info("endereco atualizado");
        return new ResponseEntity<>(enderecoDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{idEndereco}") // localhost:8080/endereco/10 OK
    public ResponseEntity<EnderecoDTO> delete(@PathVariable("idEndereco") Integer id) throws Exception {
        log.info("Deletando endereço...");
        enderecoService.delete(id);
        log.info("Endereço deletado!");
        return ResponseEntity.noContent().build();
    }
}
