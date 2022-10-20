package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Veiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {
    private VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {

        this.veiculoRepository = veiculoRepository;
    }

    // criação de um objeto
    public void adicionarVeiculo(Veiculo veiculo) {
        try {
            Veiculo veiculoAdicionado = veiculoRepository.adicionar(veiculo);
            System.out.println("veiculo adicinado com sucesso! " + veiculoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
//            System.out.println("TRACE: ");
//            e.printStackTrace();
        }
    }

    // remoção
    public void removerVeiculo(Integer id) {
        try {
            boolean conseguiuRemover = veiculoRepository.remover(id);
            System.out.println("veiculo removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // atualização de um objeto
    public void editarVeiculo(Integer id, Veiculo veiculo) {
        try {
            boolean conseguiuEditar = veiculoRepository.editar(id, veiculo);
            System.out.println("veiculo editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // leitura
    public void listarVeiculos() {
        try {
            List<Veiculo> listar = veiculoRepository.listar();
            listar.forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listarVeiculosDisponiveis() {
        try {
            List<Veiculo> listar = veiculoRepository.listarVeiculosDisponiveis();
            listar.forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }
}
