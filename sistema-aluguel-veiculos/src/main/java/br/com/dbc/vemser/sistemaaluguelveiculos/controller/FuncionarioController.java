package br.com.dbc.vemser.sistemaaluguelveiculos.controller;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.FuncionarioService;
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
@RequestMapping("/funcionario") // localhost:8080/funcionario
public class FuncionarioController implements FuncionarioControllerInterface{

    private final FuncionarioService funcionarioService;

    @GetMapping // localhost:8080/funcionario OK
    public List<FuncionarioDTO> list() throws BancoDeDadosException {
        return funcionarioService.list();
    }

    @PostMapping // localhost:8080/funcionario/4 OK
    public ResponseEntity<FuncionarioDTO> create(@Valid @RequestBody FuncionarioCreateDTO funcionarioCreateDTO) throws Exception {
        log.info("Criando funcionário...");
        FuncionarioDTO funcionarioDTO = funcionarioService.create(funcionarioCreateDTO);
        log.info("Funcionário criado!");
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.OK);
    }

    @PutMapping("/{idFuncionario}") // localhost:8080/funcionario/1000 OK
    public ResponseEntity<FuncionarioDTO> update(@PathVariable("idFuncionario") Integer id,
                                              @Valid @RequestBody FuncionarioDTO funcionarioAtualizar) throws Exception {
        log.info("Atualizando funcionário...");
        FuncionarioDTO funcionarioDTO = funcionarioService.update(id, funcionarioAtualizar);
        log.info("Funcionário atualizado!");
        return new ResponseEntity<>(funcionarioDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{idFuncionario}") // localhost:8080/funcionario/10 OK
    public ResponseEntity<FuncionarioDTO> delete(@PathVariable("idFuncionario") Integer id) throws Exception {
        log.info("Deletando funcionário...");
        funcionarioService.delete(id);
        log.info("Funcionário deletado!");
        return ResponseEntity.noContent().build();
    }
}
