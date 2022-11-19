package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CargoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.FuncionarioEntity;

public class FuncionarioFactory {
    public static FuncionarioEntity getFuncionarioEntity() {
        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome("ADMIN");
        funcionarioEntity.setCpf("11122233344");
        funcionarioEntity.setNome("Michael Jackson");
        funcionarioEntity.setMatricula(13);
        funcionarioEntity.setSenha("123");
        funcionarioEntity.setEmail("michael@gmail.com.br");
        funcionarioEntity.setCargoEntity(cargoEntity);
        funcionarioEntity.setIdFuncionario(10);
        funcionarioEntity.setAtivo('T');
        return funcionarioEntity;
    }
}
