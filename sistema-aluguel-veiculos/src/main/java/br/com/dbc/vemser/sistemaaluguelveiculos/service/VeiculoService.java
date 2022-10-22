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

    public Veiculo create(Veiculo veiculo) throws Exception {
        findById(veiculo.getIdVeiculo());
        return veiculoRepository.create(veiculo);
    }

    private Veiculo findById(Integer id) throws Exception {
        Veiculo veiculoRecuperado = veiculoRepository.list().stream()
                .filter(veiculo -> veiculo.getIdVeiculo().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Veiculo não encontrado"));
        return veiculoRecuperado;
    }

    public Veiculo update(Integer id, Veiculo veiculoAtualizar) throws Exception {
        Veiculo veiculoRecuperado = findById(id);
        veiculoRecuperado.setIdVeiculo(veiculoAtualizar.getIdVeiculo());
        veiculoRecuperado.setDisponibilidadeVeiculo(veiculoAtualizar.getDisponibilidadeVeiculo());
        veiculoRecuperado.setMarca(veiculoAtualizar.getMarca());
        veiculoRecuperado.setAno(veiculoAtualizar.getAno());
        veiculoRecuperado.setModelo(veiculoAtualizar.getModelo());
        veiculoRecuperado.setPlaca(veiculoAtualizar.getPlaca());
        veiculoRecuperado.setValorLocacao(veiculoAtualizar.getValorLocacao());
        veiculoRecuperado.setQuilometragem(veiculoAtualizar.getQuilometragem());

        return veiculoRecuperado;
    }

    public void delete(Integer id) throws Exception {
        Veiculo veiculoDeletado = findById(id);
        veiculoRepository.delete(veiculoDeletado.getIdVeiculo());
    }

       public List<Veiculo> list() throws BancoDeDadosException {
        return veiculoRepository.list();
    }
//    public List<Veiculo> listByVeiculo(Integer id){
//        return veiculoRepository.listByVeiculo(id);
//    }

}
