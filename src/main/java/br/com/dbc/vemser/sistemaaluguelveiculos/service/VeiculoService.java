package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Veiculo;
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

    public VeiculoDTO create(VeiculoCreateDTO veiculo) {
        try {
            Veiculo veiculoEntity = objectMapper.convertValue(veiculo, Veiculo.class);
            VeiculoDTO veiculoDTO = objectMapper.convertValue(veiculoRepository.create(veiculoEntity), VeiculoDTO.class);
            return veiculoDTO;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return null;
    }

    public VeiculoDTO update(Integer idVeiculo, VeiculoCreateDTO veiculo) throws RegraDeNegocioException {
        try {
            Veiculo veiculoRecuperado = veiculoRepository.getPorId(idVeiculo);

            if(veiculoRecuperado.getIdVeiculo() != null) {
                Veiculo veiculoEntity = objectMapper.convertValue(veiculo, Veiculo.class);
                VeiculoDTO veiculoDTO = objectMapper.convertValue(veiculoRepository.update(idVeiculo, veiculoEntity), VeiculoDTO.class);
                return veiculoDTO;
            }else {
                throw new RegraDeNegocioException("Veículo não encontrado!");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer idVeiculo) throws RegraDeNegocioException {
        try {
            Veiculo veiculoRecuperado = veiculoRepository.getPorId(idVeiculo);

            if(veiculoRecuperado.getIdVeiculo() != null) {
                veiculoRepository.delete(idVeiculo);
            }else {
                throw new RegraDeNegocioException("Veículo não encontrado!");
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public List<VeiculoDTO> list() {
        try {
            return veiculoRepository.list().stream()
                    .map(veiculo -> objectMapper.convertValue(veiculo, VeiculoDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }
}
