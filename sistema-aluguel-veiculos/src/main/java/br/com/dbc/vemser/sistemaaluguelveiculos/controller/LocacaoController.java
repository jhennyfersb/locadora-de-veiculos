package br.com.dbc.vemser.sistemaaluguelveiculos.controller;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
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
@RequestMapping("/locacao") // localhost:8080/locacao
public class LocacaoController implements LocacaoControllerInterface{

    private final LocacaoService locacaoService;

    @GetMapping // localhost:8080/locacao OK
    public List<LocacaoDTO> list() throws BancoDeDadosException {
        return locacaoService.list();
    }

//    @GetMapping("/{idFuncionario}") // localhost:8080/endereco/2 OK
//    public List<FuncionarioDTO> listByIdFuncionario(@PathVariable("idFuncionario") Integer id) {
//        return funcionarioService.listByIdEndereco(id);
//    }

    @PostMapping // localhost:8080/locacao/4 OK
    public ResponseEntity<LocacaoDTO> create(@Valid @RequestBody LocacaoCreateDTO locacaoCreateDTO) throws Exception {
        log.info("Criando locação...");
        LocacaoDTO locacaoDTO = locacaoService.create(locacaoCreateDTO);
        log.info("Locação criada");
        return new ResponseEntity<>(locacaoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idLocacao}") // localhost:8080/locacao/1000 OK
    public ResponseEntity<LocacaoDTO> update(@PathVariable("idLocacao") Integer id,
                                             @Valid @RequestBody LocacaoCreateDTO locacaoAtualizar) throws Exception {
        log.info("Atualizando locação...");
        LocacaoDTO locacaoDTO = locacaoService.update(id, locacaoAtualizar);
        log.info("Locação atualizado");
        return new ResponseEntity<>(locacaoDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{idLocacao}") // localhost:8080/locacao/10 OK
    public ResponseEntity<LocacaoDTO> delete(@PathVariable("idLocacao") Integer id) throws Exception {
        log.info("Deletando locação...");
        locacaoService.delete(id);
        log.info("Locação deletada");
        return ResponseEntity.noContent().build();
    }
}
