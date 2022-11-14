package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.FuncionarioControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
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
@RequestMapping("/funcionario")
public class FuncionarioController implements FuncionarioControllerInterface {
    private final FuncionarioService funcionarioService;

    @GetMapping
    public List<FuncionarioDTO> list() throws RegraDeNegocioException {
        return funcionarioService.list();
    }

    @GetMapping("/{idFuncionario}")
    public FuncionarioDTO listByIdFuncionario(@PathVariable("idFuncionario") Integer id) throws RegraDeNegocioException {
        return funcionarioService.findById(id);
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> create(@Valid @RequestBody FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        log.info("Criando funcionário...");
        FuncionarioDTO funcionarioDTO = funcionarioService.create(funcionarioCreateDTO);
        log.info("Funcionário criado!");
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.OK);
    }

    @PutMapping("/{idFuncionario}")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable("idFuncionario") Integer id,
                                                 @Valid @RequestBody FuncionarioCreateDTO funcionarioAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando funcionário...");
        FuncionarioDTO funcionarioDTO = funcionarioService.update(id, funcionarioAtualizar);
        log.info("Funcionário atualizado!");
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity<FuncionarioDTO> delete(@PathVariable("idFuncionario") Integer id) throws RegraDeNegocioException {
        log.info("Deletando funcionário...");
        funcionarioService.delete(id);
        log.info("Funcionário deletado!");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ativar-funcionario/{idFuncionario}")
    public ResponseEntity<FuncionarioDTO> ativarFuncionario(@PathVariable("idFuncionario") Integer id) {
        char ativo = 'T';
        log.info("Atualizando funcionário...");
        FuncionarioDTO funcionarioDTO = funcionarioService.setAtivoFuncionario(id, ativo);
        log.info("Funcionário ATIVO!");
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.OK);
    }

    @PutMapping("/desativar-funcionario/{idFuncionario}")
    public ResponseEntity<FuncionarioDTO> desativarFuncionario(@PathVariable("idFuncionario") Integer id) {
        char desativado = 'F';
        log.info("Atualizando funcionário...");
        FuncionarioDTO funcionarioDTO = funcionarioService.setAtivoFuncionario(id, desativado);
        log.info("Funcionário DESATIVADO!");
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.OK);
    }
}
