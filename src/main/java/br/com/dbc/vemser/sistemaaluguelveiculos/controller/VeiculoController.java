package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.VeiculoControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.VeiculoService;
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
@RequestMapping("/veiculo")
public class VeiculoController /*implements VeiculoControllerInterface */{
    private final VeiculoService veiculoService;

    @GetMapping
    public List<VeiculoDTO> list() throws RegraDeNegocioException {
        return veiculoService.list();
    }

    @GetMapping("/veiculos-por-disponibilidade")
    public List<VeiculoDTO> listarVeiculosPorDisponibilidade(@RequestParam("disponibilidade")DisponibilidadeVeiculo disponibilidade) throws RegraDeNegocioException {
        return veiculoService.listVeiculosDisponiveis(disponibilidade);
    }

    @GetMapping("/{idVeiculo}")
    public VeiculoDTO listByIdVeiculo(@PathVariable("idVeiculo") Integer id) throws RegraDeNegocioException {
        return veiculoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<VeiculoDTO> create(@Valid @RequestBody VeiculoCreateDTO veiculoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando veículo...");
        VeiculoDTO veiculoDTO = veiculoService.create(veiculoCreateDTO);
        log.info("Veículo criado!");
        return new ResponseEntity<>(veiculoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idVeiculo}")
    public ResponseEntity<VeiculoDTO> update(@PathVariable("idVeiculo") Integer idVeiculo,
                                             @Valid @RequestBody VeiculoCreateDTO veiculoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando veículo...");
        VeiculoDTO veiculoDTO = veiculoService.update(idVeiculo, veiculoAtualizar);
        log.info("Veículo atualizado!");
        return new ResponseEntity<>(veiculoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idVeiculo}")
    public ResponseEntity<VeiculoDTO> delete(@PathVariable("idVeiculo") Integer idVeiculo) throws RegraDeNegocioException {
        log.info("Deletando veículo...");
        veiculoService.delete(idVeiculo);
        log.info("Veículo deletado!");
        return ResponseEntity.noContent().build();
    }
}
