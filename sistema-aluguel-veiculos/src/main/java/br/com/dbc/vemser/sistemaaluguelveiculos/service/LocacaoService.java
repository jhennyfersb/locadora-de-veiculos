package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Locacao;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.LocacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocacaoService {
    private LocacaoRepository locacaoRepository;

    public LocacaoService(LocacaoRepository locacaoRepository) {

        this.locacaoRepository = locacaoRepository;
    }

    public void create(Locacao locacao) {
        try {
            Locacao locacaoAdicionado = locacaoRepository.create(locacao);
            System.out.println("locação adicinado com sucesso! \n" + locacaoAdicionado);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        try {
            boolean conseguiuRemover = locacaoRepository.delete(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, Locacao locacao) {
        try {
            boolean conseguiuEditar = locacaoRepository.update(id, locacao);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void list() {
        try {
            List<Locacao> list = locacaoRepository.list();
            list.forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

}
