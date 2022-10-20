package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    // criação de um objeto
    public void adicionarFuncionario(Funcionario funcionario) {
        try {

            if (funcionario.getCpf().length() != 11) {
                throw new Exception("CPF Invalido!");
            }

            Funcionario funcionarioAdicionado = funcionarioRepository.adicionar(funcionario);
            System.out.println("funcionario adicinado com sucesso! " + funcionarioAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
//            System.out.println("TRACE: ");
//            e.printStackTrace();
        }
    }

    // remoção
    public void removerFuncionario(Integer id) {
        try {
            boolean conseguiuRemover = funcionarioRepository.remover(id);
            System.out.println("funcionario removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // atualização de um objeto
    public void editarFuncionario(Integer id, Funcionario funcionario) {
        try {
            boolean conseguiuEditar = funcionarioRepository.editar(id, funcionario);
            System.out.println("funcionario editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // leitura
    public void listarFuncionarios() {
        try {
            List<Funcionario> listar = funcionarioRepository.listar();
            listar.forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }
}
