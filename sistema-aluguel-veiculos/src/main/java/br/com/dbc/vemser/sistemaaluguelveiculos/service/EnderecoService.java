package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Endereco;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    private EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {

        this.enderecoRepository = enderecoRepository;
    }

    public void adicionarEndereco(Endereco endereco) {
        try {
            if(!validarEndereco(endereco)){
                throw new RuntimeException("Endereço Inválido");
            }
            Endereco enderecoAdicionado = enderecoRepository.adicionar(endereco);
            System.out.println("Endereço adicinado com sucesso! " + enderecoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = enderecoRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void removerEnderecosOciosos() throws BancoDeDadosException {
        try {
            enderecoRepository.listarEnderecoSemVinculo()
                    .stream()
                    .forEach(enderecos -> remover(enderecos.getId_endereco()));
        }
        catch (BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public void editar(Integer id, Endereco endereco) {
        try {
            boolean conseguiuEditar = enderecoRepository.editar(id, endereco);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listar() {
        try {
            enderecoRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public int retornarId(){
        try{
            return enderecoRepository.retornarUltimoIdRegistrado();
        }
        catch (BancoDeDadosException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void listarSemVinculo() {
        try {
            enderecoRepository.listarEnderecoSemVinculo().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }
    public boolean validarEndereco (Endereco endereco){
        if(endereco.getCep().length() == 9){
            return endereco.getCep().charAt(5) != '-';
        }
        return false;
    }
}
