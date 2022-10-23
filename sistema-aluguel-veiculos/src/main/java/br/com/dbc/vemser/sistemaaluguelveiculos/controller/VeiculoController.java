package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Veiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.VeiculoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@Service
@RequestMapping("/veiculo")
public class VeiculoController {
    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public List<Veiculo> list() throws BancoDeDadosException {
        return veiculoService.list();
    }
//
//    @GetMapping("/{idVeiculo}")
//    public List<Veiculo> listVeiculoId(@PathVariable("idVeiculo") Integer id) {
//        return veiculoService.listByVeiculo(id);
//    }
    @PostMapping
    public ResponseEntity<Veiculo> create(@Valid @RequestBody Veiculo veiculo) throws Exception {
        log.info("Criando veículo...");
        Veiculo novoVeiculo = veiculoService.create(veiculo);
        log.info("Veículo criado");
        return new ResponseEntity<>(novoVeiculo, HttpStatus.OK);
    }

    @PutMapping("/{idVeiculo}")
    public ResponseEntity<Veiculo> update(@PathVariable("idVeiculo") Integer id,
                                          @RequestBody Veiculo veiculoAtualizar) throws Exception {
        //log.info("atualizando veiculo... ");
        Veiculo veiculo = veiculoService.update(id, veiculoAtualizar);
        //log.info("veiculo atualizado");
        return new ResponseEntity<>(veiculoAtualizar, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idVeiculo}")
    public ResponseEntity<Veiculo> delete(@Valid @PathVariable("idVeiculo") Integer idVeiculo) throws Exception {
        veiculoService.delete(idVeiculo);
        return ResponseEntity.noContent().build();
    }

}





