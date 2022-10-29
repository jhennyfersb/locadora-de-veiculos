package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Veiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
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

    public VeiculoDTO create(VeiculoCreateDTO veiculo) throws RegraDeNegocioException {
        try {
            Veiculo veiculoEntity = converterEntity(veiculo);
            return converterEmDTO(veiculoRepository.create(veiculoEntity));

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
//        } catch (Exception e) {
//            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public VeiculoDTO update(Integer idVeiculo, VeiculoCreateDTO veiculo) throws RegraDeNegocioException {
        try {
            veiculoRepository.findById(idVeiculo);

            Veiculo veiculoEntity = converterEntity(veiculo);
            return converterEmDTO(veiculoRepository.update(idVeiculo, veiculoEntity));

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
    }

    public void delete(Integer idVeiculo) throws RegraDeNegocioException {
        try {
            veiculoRepository.findById(idVeiculo);

            veiculoRepository.delete(idVeiculo);

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public List<VeiculoDTO> list() throws RegraDeNegocioException {
        try {
            return veiculoRepository.list().stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public Veiculo converterEntity(VeiculoCreateDTO veiculoCreateDTO){
        return objectMapper.convertValue(veiculoCreateDTO, Veiculo.class);
    }

    public VeiculoDTO converterEmDTO(Veiculo veiculo){
        return objectMapper.convertValue(veiculo, VeiculoDTO.class);
    }

    public VeiculoDTO findById(Integer id) throws RegraDeNegocioException{
        try {
            Veiculo veiculoRecuperado = veiculoRepository.findById(id);

            if(veiculoRecuperado.getIdVeiculo() != null) {
                return converterEmDTO(veiculoRecuperado);
            }else {
                throw new RegraDeNegocioException("Veículo não encontrado");
            }
        }catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");        }
    }
}
