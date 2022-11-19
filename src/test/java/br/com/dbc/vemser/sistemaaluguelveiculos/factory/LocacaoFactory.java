package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LocacaoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.LocacaoEntity;

import java.time.LocalDate;

public class LocacaoFactory {
    public static LocacaoEntity getLocacaoEntity(){
        LocacaoEntity locacaoEntity = new LocacaoEntity(1,
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                ClienteFactory.getClienteEntity(),
                VeiculoFactory.getVeiculo(),
                CartaoCreditoFactory.getCartaoCreditoEntity(),
                FuncionarioFactory.getFuncionarioEntity());
        locacaoEntity.setValorLocacao(234.00);
        return locacaoEntity;
    }
    public static LocacaoCreateDTO getLocacaoCreateDTO(){
       return new LocacaoCreateDTO(LocalDate.now(),
                LocalDate.now().plusDays(2),
                2,
                2,
                4);
    }

}
