package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.ClienteControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController implements ClienteControllerInterface {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> list() throws RegraDeNegocioException {
        return clienteService.list();
    }

    @GetMapping("relatorio-cliente")
    public List<RelatorioClienteDTO> relatorioCliente(@RequestParam(required = false) Integer idCliente) {
        return clienteService.relatorioCliente(idCliente);
    }

    @GetMapping("/{idCliente}")
    public ClienteDTO listByIdCliente(@PathVariable("idCliente") Integer id) throws RegraDeNegocioException {
        return clienteService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        log.info("Criando cliente...");
        ClienteDTO clienteDTO = clienteService.create(clienteCreateDTO);
        log.info("Cliente criado");
        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable("idCliente") Integer id,
                                             @Valid @RequestBody ClienteCreateDTO clienteAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando cliente...");
        ClienteDTO clienteDTO = clienteService.update(id, clienteAtualizar);
        log.info("Cliente atualizado");
        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> delete(@PathVariable("idCliente") Integer id) throws RegraDeNegocioException {
        log.info("Deletando cliente...");
        clienteService.delete(id);
        log.info("Cliente deletado");
        return ResponseEntity.noContent().build();
    }
}
