package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.PageDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.VeiculoDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.VeiculoEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.DisponibilidadeVeiculo;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.VeiculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {
    private final VeiculoRepository veiculoRepository;
    private final ObjectMapper objectMapper;
    private final LogService logService;

    public VeiculoDTO create(VeiculoCreateDTO veiculo) throws RegraDeNegocioException {
        VeiculoEntity veiculoEntity = converterEntity(veiculo);
        veiculoEntity.setDisponibilidadeVeiculo(DisponibilidadeVeiculo.valueOf("DISPONIVEL"));
        VeiculoDTO veiculoDTO = converterEmDTO(veiculoRepository.save(veiculoEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.CREATE,"CPF logado: "+cpf, EntityLog.VEICULO));
        return veiculoDTO;
    }

    public VeiculoDTO update(Integer idVeiculo, VeiculoCreateDTO veiculo) throws RegraDeNegocioException {
        this.findById(idVeiculo,false);
        VeiculoEntity veiculoEntity = converterEntity(veiculo);
        veiculoEntity.setIdVeiculo(idVeiculo);
        VeiculoDTO veiculoDTO = converterEmDTO(veiculoRepository.save(veiculoEntity));
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.UPDATE,"CPF logado: "+cpf, EntityLog.VEICULO));
        return veiculoDTO;
    }

    public void delete(Integer idVeiculo) throws RegraDeNegocioException {
        findById(idVeiculo,false);
        veiculoRepository.deleteById(idVeiculo);
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.DELETE,"CPF logado: "+cpf, EntityLog.VEICULO));
    }

    public PageDTO<VeiculoDTO> list(Integer pagina, Integer tamanho) throws RegraDeNegocioException {

        Sort ordenacao = Sort.by("idVeiculo");
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<VeiculoEntity> listar = veiculoRepository.findAll(pageRequest);
        List<VeiculoDTO> veiculoPagina = listar.stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ,"CPF logado: "+cpf, EntityLog.VEICULO));
        return new PageDTO<>(listar.getTotalElements(),
                listar.getTotalPages(),
                pagina,
                tamanho,
                veiculoPagina);

    }

    public List<VeiculoDTO> listVeiculosDisponiveis(DisponibilidadeVeiculo disponibilidadeVeiculo) {

        List<VeiculoDTO> lista = veiculoRepository.retornarVeiculosPorDisponibilidade(disponibilidadeVeiculo)
                .stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

        String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        logService.salvarLog(new LogCreateDTO(TipoLog.READ,"CPF logado: "+cpf, EntityLog.VEICULO));
        return lista;
    }

    public VeiculoEntity converterEntity(VeiculoCreateDTO veiculoCreateDTO) {
        return objectMapper.convertValue(veiculoCreateDTO, VeiculoEntity.class);
    }

    public VeiculoDTO converterEmDTO(VeiculoEntity veiculo) {
        return objectMapper.convertValue(veiculo, VeiculoDTO.class);
    }

    public VeiculoDTO findById(Integer id,boolean gerarLog) throws RegraDeNegocioException {

        Optional<VeiculoEntity> veiculoRecuperado = veiculoRepository.findById(id);
        if (veiculoRecuperado == null) {
            throw new RegraDeNegocioException("Veículo não encontrado");
        }
        if(gerarLog){
            String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            logService.salvarLog(new LogCreateDTO(TipoLog.READ,"CPF logado: "+cpf, EntityLog.VEICULO));
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
