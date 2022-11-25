package br.com.dbc.vemser.sistemaaluguelveiculos.controller;

import br.com.dbc.vemser.sistemaaluguelveiculos.controller.interfaces.RelatorioCupomControllerInterface;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioCupomDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.service.CupomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/cupom")
public class CupomController implements RelatorioCupomControllerInterface {
    private final CupomService cupomService;

    @GetMapping
    public List<RelatorioCupomDTO> relatorioCupomStatus(){
        return cupomService.relatorioCupomStatus();
    }

}
