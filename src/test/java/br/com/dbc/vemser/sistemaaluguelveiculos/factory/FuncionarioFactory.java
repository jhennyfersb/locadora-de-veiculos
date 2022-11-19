package br.com.dbc.vemser.sistemaaluguelveiculos.factory;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
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
    public static FuncionarioCreateDTO getFuncionarioCreateDTO() {
        FuncionarioCreateDTO funcionarioCreateDTO = new FuncionarioCreateDTO();
        funcionarioCreateDTO.setCpf("11122233344");
        funcionarioCreateDTO.setNome("Michael Jackson");
        funcionarioCreateDTO.setMatricula(13);
        funcionarioCreateDTO.setSenha("123");
        funcionarioCreateDTO.setEmail("michael@gmail.com.br");
        funcionarioCreateDTO.setIdCargo(1);
        return funcionarioCreateDTO;
    }
}
