package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
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
    public List<VeiculoDTO> list() throws BancoDeDadosException, RegraDeNegocioException {
        return veiculoService.list();
    }
//
//    @GetMapping("/{idVeiculo}")
//    public List<Veiculo> listVeiculoId(@PathVariable("idVeiculo") Integer id) {
//        return veiculoService.listByVeiculo(id);
//    }
    @PostMapping
    public ResponseEntity<VeiculoDTO> create(@Valid @RequestBody VeiculoCreateDTO veiculoCreateDTO) throws Exception {
        log.info("Criando veículo...");
        VeiculoDTO veiculoDTO = veiculoService.create(veiculoCreateDTO);
        log.info("Veículo criado");
        return new ResponseEntity<>(veiculoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idVeiculo}")
    public ResponseEntity<VeiculoDTO> update(@PathVariable("idVeiculo") Integer id,
                                          @RequestBody VeiculoCreateDTO veiculoAtualizar) throws Exception {
        log.info("atualizando veiculo... ");
        VeiculoDTO veiculoDTO = veiculoService.update(id, veiculoAtualizar);
        log.info("veiculo atualizado");
        return new ResponseEntity<>(veiculoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idVeiculo}")
    public ResponseEntity<VeiculoDTO> delete(@Valid @PathVariable("idVeiculo") Integer idVeiculo) throws Exception {
        veiculoService.delete(idVeiculo);
        return ResponseEntity.noContent().build();
    }

}





