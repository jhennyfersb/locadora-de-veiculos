package br.com.dbc.vemser.sistemaaluguelveiculos.service;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.FuncionarioDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Funcionario;
import br.com.dbc.vemser.sistemaaluguelveiculos.model.Veiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.VeiculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    private final ObjectMapper objectMapper;

    public VeiculoDTO create(VeiculoCreateDTO veiculo) throws Exception {
        Veiculo veiculoAdicionado = veiculoRepository.create(converterEmVeiculo(veiculo));
        //findById(veiculo.getIdVeiculo());
        return converterEmDTO(veiculoAdicionado);
    }

    private Veiculo findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Veiculo veiculoRecuperado = veiculoRepository.list().stream()
                .filter(veiculo -> veiculo.getIdVeiculo().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Veiculo não encontrado"));
        return veiculoRecuperado;
    }

    public VeiculoDTO update(Integer id, VeiculoCreateDTO veiculoAtualizar) throws RegraDeNegocioException, BancoDeDadosException {
        Veiculo veiculoRecuperado = findById(id);
       // veiculoRecuperado.setIdVeiculo(veiculoAtualizar.getIdVeiculo());
        veiculoRecuperado.setDisponibilidadeVeiculo(veiculoAtualizar.getDisponibilidadeVeiculo());
        veiculoRecuperado.setMarca(veiculoAtualizar.getMarca());
        veiculoRecuperado.setAno(veiculoAtualizar.getAno());
        veiculoRecuperado.setModelo(veiculoAtualizar.getModelo());
        veiculoRecuperado.setPlaca(veiculoAtualizar.getPlaca());
        veiculoRecuperado.setValorLocacao(veiculoAtualizar.getValorLocacao());
        veiculoRecuperado.setQuilometragem(veiculoAtualizar.getQuilometragem());
        return objectMapper.convertValue(veiculoRecuperado,VeiculoDTO.class);
    }

    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Veiculo veiculoDeletado = findById(id);
        veiculoRepository.delete(veiculoDeletado.getIdVeiculo());
    }

       public List<VeiculoDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return veiculoRepository.list().stream().map(veiculo -> objectMapper.convertValue(veiculo,VeiculoDTO.class)).collect(Collectors.toList());
    }
    public Veiculo converterEmVeiculo(VeiculoCreateDTO veiculoCreateDTO){
        return objectMapper.convertValue(veiculoCreateDTO, Veiculo.class);
    }

    public VeiculoDTO converterEmDTO(Veiculo veiculo){
        return objectMapper.convertValue(veiculo, VeiculoDTO.class);
    }
//    public List<Veiculo> listByVeiculo(Integer id){
//        return veiculoRepository.listByVeiculo(id);
//    }

}
