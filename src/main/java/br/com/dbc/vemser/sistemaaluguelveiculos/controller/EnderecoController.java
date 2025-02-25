package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.EnderecoControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.PageDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/endereco")
public class EnderecoController implements EnderecoControllerInterface {

    private final EnderecoService enderecoService;

    @GetMapping
    public PageDTO<EnderecoDTO> list(@RequestParam(defaultValue = "0") Integer pagina,
                                     @RequestParam(defaultValue = "20") Integer tamanho) throws RegraDeNegocioException {
        return enderecoService.list(pagina, tamanho);
    }

    @GetMapping("/{idEndereco}")
    public EnderecoDTO listByIdEndereco(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException {
        return enderecoService.findDtoById(id);
    }

    @GetMapping("/{idCliente}/cliente")
    public PageDTO<EnderecoDTO> findEnderecoByIdCliente(@PathVariable("idCliente") Integer idCliente,
                                                        @RequestParam(defaultValue = "0") Integer pagina,
                                                        @RequestParam(defaultValue = "20") Integer tamanho) throws RegraDeNegocioException {
        return enderecoService.findEnderecoByIdCliente(idCliente, pagina, tamanho);
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> create(@Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando endereco...");
        EnderecoDTO enderecoDTO = enderecoService.create(enderecoCreateDTO);
        log.info("Endereço criado");
        return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id,
                                              @Valid @RequestBody EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando endereco...");
        EnderecoDTO enderecoDTO = enderecoService.update(id, enderecoAtualizar);
        log.info("Endereço atualizado");
        return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> delete(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException {
        log.info("Deletando endereço...");
        enderecoService.delete(id);
        log.info("Endereço deletado!");
        return ResponseEntity.noContent().build();
    }
}
