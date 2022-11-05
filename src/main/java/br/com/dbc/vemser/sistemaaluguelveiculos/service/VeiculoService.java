package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.VeiculoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.VeiculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {
    private final VeiculoRepository veiculoRepository;
    private final ObjectMapper objectMapper;

    public VeiculoDTO create(VeiculoCreateDTO veiculo) throws RegraDeNegocioException {
        try {
            VeiculoEntity veiculoEntity = converterEntity(veiculo);
            veiculoEntity.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.valueOf("DISPONIVEL"));
            return converterEmDTO(veiculoRepository.save(veiculoEntity));

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao criar no banco de dados.");
        }
    }

    public VeiculoDTO update(Integer idVeiculo, VeiculoCreateDTO veiculo) throws RegraDeNegocioException {
        try {
            this.findById(idVeiculo);
            VeiculoEntity veiculoEntity = converterEntity(veiculo);
            veiculoEntity.setIdVeiculo(idVeiculo);
            return converterEmDTO(veiculoRepository.save(veiculoEntity));

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao editar no banco de dados.");
        }
    }

    public void delete(Integer idVeiculo) throws RegraDeNegocioException {
        try {
            findById(idVeiculo);
            veiculoRepository.deleteById(idVeiculo);

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao deletar no banco de dados.");
        }
    }

    public List<VeiculoDTO> list() throws RegraDeNegocioException {
        try {
            return veiculoRepository.findAll().stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public List<VeiculoDTO> listVeiculosDisponiveis(DisponibilidadeVeiculo disponibilidadeVeiculo) throws RegraDeNegocioException {
        try {
            return veiculoRepository.retornarVeiculosPorDisponibilidade(disponibilidadeVeiculo)
                    .stream()
                    .map(this::converterEmDTO)
                    .collect(Collectors.toList());

        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao listar no banco de dados.");
        }
    }

    public VeiculoEntity converterEntity(VeiculoCreateDTO veiculoCreateDTO) {
        return objectMapper.convertValue(veiculoCreateDTO, VeiculoEntity.class);
    }

    public VeiculoDTO converterEmDTO(VeiculoEntity veiculo) {
        return objectMapper.convertValue(veiculo, VeiculoDTO.class);
    }

    public VeiculoDTO findById(Integer id) throws RegraDeNegocioException {
        try {
            Optional<VeiculoEntity> veiculoRecuperado = veiculoRepository.findById(id);

            if (veiculoRecuperado == null) {
                throw new RegraDeNegocioException("Veículo não encontrado");
            }
            return objectMapper.convertValue(veiculoRecuperado,VeiculoDTO.class);
        } catch (PersistenceException e) {
            throw new RegraDeNegocioException("Erro ao encontrar no banco de dados.");
        }
    }
}
