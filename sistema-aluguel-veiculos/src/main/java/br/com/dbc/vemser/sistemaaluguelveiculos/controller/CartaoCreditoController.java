//package br.com.dbc.vemser.sistemaaluguelveiculos.controller;
//
//import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CartaoCreditoCreateDTO;
//import br.com.dbc.vemser.sistemaaluguelveiculos.model.CartaoCredito;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@Validated
//@RequiredArgsConstructor
//@RequestMapping("/cartao")
//public class CartaoCreditoController {
//
//    private final CartaoCredito cartaoCredito;
//
//    @GetMapping // localhost:8080/cartao
//    public List<CartaoCreditoDTO> list() {
//        return cartaoCredito.list();
//
//    @PostMapping // localhost:8080/cartao
//    public ResponseEntity<CartaoCreditoDTO> create(CartaoCreditoCreateDTO cartaoCreditoCreateDTO) throws Exception {
//        log.info("Criando cartão de crédito...");
//        CartaoCreditoDTO cartao = funcionarioService.create(cartaoCreditoCreateDTO);
//        log.info("Cartão de crédito criado");
//        return new ResponseEntity<>(cartao, HttpStatus.OK);
//    }
////
////    @PutMapping("/{idFuncionario}") // localhost:8080/endereco/1000 OK
////    public ResponseEntity<CartaoCreditoDTO> update(@PathVariable("idFuncionario") Integer id,
////                                                 @Valid @RequestBody FuncionarioDTO funcionarioAtualizar) throws Exception {
////        log.info("Atualizando funcionário...");
////        FuncionarioDTO f = funcionarioService.update(id, funcionarioAtualizar);
////        log.info("funcionário atualizado");
////        return new ResponseEntity<>(f,HttpStatus.OK);
////    }
////
////    @DeleteMapping("/{idEndereco}") // localhost:8080/endereco/10 OK
////    public ResponseEntity<FuncionarioDTO> delete(@PathVariable("idFuncionario") Integer id) throws Exception {
////        log.info("Deletando endereço...");
////        funcionarioService.delete(id);
////        log.info("Endereço deletado");
////        return ResponseEntity.noContent().build();
//    }
//}
