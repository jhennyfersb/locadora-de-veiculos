package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Contato;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.ContatoRepository;
import org.springframework.stereotype.Service;

@Service
public class ContatoService {
    private ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public void adicionarContato(Contato contato) {
        try {
            Contato contatoAdicionado = contatoRepository.adicionar(contato);
            System.out.println("Contato adicinado com sucesso! " + contatoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = contatoRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void removerContatosOciosos() throws BancoDeDadosException {
        try {
            contatoRepository.listarContatoSemVinculo()
                    .stream()
                    .forEach(enderecos -> remover(enderecos.getId_contato()));
        }
        catch (BancoDeDadosException e){
            e.printStackTrace();
        }
    }

    public void editar(Integer id, Contato contato) {
        try {
            boolean conseguiuEditar = contatoRepository.editar(id, contato);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listar() {
        try {
            contatoRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listarSemVinculo() {
        try {
            contatoRepository.listarContatoSemVinculo().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public int retornarId(){
        try{
            return contatoRepository.retornarUltimoIdRegistrado();
        }
        catch (BancoDeDadosException e){
            e.printStackTrace();
        }
        return 0;
    }

}
