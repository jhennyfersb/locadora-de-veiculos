package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.EnderecoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.PageDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.EnderecoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.VeiculoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.VeiculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

        VeiculoEntity veiculoEntity = converterEntity(veiculo);
        veiculoEntity.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.valueOf("DISPONIVEL"));
        return converterEmDTO(veiculoRepository.save(veiculoEntity));


    }

    public VeiculoDTO update(Integer idVeiculo, VeiculoCreateDTO veiculo) throws RegraDeNegocioException {
        this.findById(idVeiculo);
        VeiculoEntity veiculoEntity = converterEntity(veiculo);
        veiculoEntity.setIdVeiculo(idVeiculo);
        return converterEmDTO(veiculoRepository.save(veiculoEntity));
    }

    public void delete(Integer idVeiculo) throws RegraDeNegocioException {
        findById(idVeiculo);
        veiculoRepository.deleteById(idVeiculo);
    }

    public PageDTO<VeiculoDTO> list(Integer pagina, Integer tamanho) throws RegraDeNegocioException {

        Sort ordenacao = Sort.by("idCliente");
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<VeiculoEntity> listar = veiculoRepository.findAll(pageRequest);
        List<VeiculoDTO> enderecoPagina = listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        return new PageDTO<>(listar.getTotalElements(),
                listar.getTotalPages(),
                pagina,
                tamanho,
                enderecoPagina);

    }

    public List<VeiculoDTO> listVeiculosDisponiveis(DisponibilidadeVeiculo disponibilidadeVeiculo) throws RegraDeNegocioException {

        return veiculoRepository.retornarVeiculosPorDisponibilidade(disponibilidadeVeiculo)
                .stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
    }

    public VeiculoEntity converterEntity(VeiculoCreateDTO veiculoCreateDTO) {
        return objectMapper.convertValue(veiculoCreateDTO, VeiculoEntity.class);
    }

    public VeiculoDTO converterEmDTO(VeiculoEntity veiculo) {
        return objectMapper.convertValue(veiculo, VeiculoDTO.class);
    }

    public VeiculoDTO findById(Integer id) throws RegraDeNegocioException {

        Optional<VeiculoEntity> veiculoRecuperado = veiculoRepository.findById(id);
        if (veiculoRecuperado == null) {
            throw new RegraDeNegocioException("Veículo não encontrado");
        }
        return objectMapper.convertValue(veiculoRecuperado, VeiculoDTO.class);

    }

    public void alterarDisponibilidadeVeiculo(VeiculoEntity veiculoEntity) {
        if (veiculoEntity.getDisponibilidadeVeiculo().getDisponibilidade() == 1) {
            veiculoEntity.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.DISPONIVEL);
        } else if (veiculoEntity.getDisponibilidadeVeiculo().getDisponibilidade() == 2) {
            veiculoEntity.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.ALUGADO);
        }
    }
}
