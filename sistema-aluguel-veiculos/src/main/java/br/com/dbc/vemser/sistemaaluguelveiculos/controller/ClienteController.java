package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.ClienteDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
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
@RequestMapping("/cliente") // localhost:8080/cliente
public class ClienteController implements ClienteControllerInterface {

    private final ClienteService clienteService;

    @GetMapping // localhost:8080/cliente OK
    public List<ClienteDTO> list() throws BancoDeDadosException {
        return clienteService.list();
    }

//    @GetMapping("/{idFuncionario}") // localhost:8080/endereco/2 OK
//    public List<FuncionarioDTO> listByIdFuncionario(@PathVariable("idFuncionario") Integer id) {
//        return funcionarioService.listByIdEndereco(id);
//    }

    @PostMapping // localhost:8080/cliente/4 OK
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteCreateDTO clienteCreateDTO) throws Exception {
        log.info("Criando cliente...");
        ClienteDTO clienteDTO = clienteService.create(clienteCreateDTO);
        log.info("Cliente criado");
        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }

    @PutMapping("/{idCliente}") // localhost:8080/cliente/1000 OK
    public ResponseEntity<ClienteDTO> update(@PathVariable("idCliente") Integer id,
                                             @Valid @RequestBody ClienteCreateDTO clienteAtualizar) throws Exception {
        log.info("Atualizando cliente...");
        ClienteDTO clienteDTO = clienteService.update(id, clienteAtualizar);
        log.info("Cliente atualizado");
        return new ResponseEntity<>(clienteDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}") // localhost:8080/cliente/10 OK
    public ResponseEntity<ClienteDTO> delete(@PathVariable("idCliente") Integer id) throws Exception {
        log.info("Deletando cliente...");
        clienteService.delete(id);
        log.info("Cliente deletado");
        return ResponseEntity.noContent().build();
    }
}
