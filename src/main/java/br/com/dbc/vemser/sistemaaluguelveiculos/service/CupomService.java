package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CupomDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioCupom;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.RelatorioCupomDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.CupomEntity;
import br.com.dbc.vemser.sistemaaluguelveiculos.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.sistemaaluguelveiculos.repository.CupomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CupomService {

    private final CupomRepository cupomRepository;

    private final EmailService emailService;

    private final ObjectMapper objectMapper;

    public CupomDTO save(CupomDTO cupomDTO) {
        CupomEntity cupomEntity = cupomRepository.save(objectMapper.convertValue(cupomDTO, CupomEntity.class));
        String base = "Olá, você acaba de ganhar um cupom de 10% de desconto para ser usado na Imobiliaria Shinigamis! Parabéns!!!<br>" +
                "Seu Cupom: " + cupomEntity.getId() + "<br>" +
                "Cupom valido até: " + cupomEntity.getDataVencimento() +
                "Cupom deve ser informado na hora do aluguel do Imovel!";
        emailService.sendEmail(base, cupomEntity.getEmail());
        return converterEmDTO(cupomEntity);
    }

    public List<RelatorioCupomDTO> relatorioCupomStatus() {
        List<RelatorioCupomDTO> relatorioCupomDTOS = cupomRepository
                .relatorioCupomStatus()
                .stream()
                .map(this::converterEmDTOCupom).toList();
        return relatorioCupomDTOS;
    }

    public CupomDTO findCupom(String idCupom) throws RegraDeNegocioException {
        CupomEntity cupomEntity = findById(idCupom);
        if (cupomEntity.isAtivo()) {
            return converterEmDTO(cupomEntity);
        } else {
            throw new RegraDeNegocioException("Cupom já utilizado!");
        }
    }

    private CupomEntity findById(String idCupom) throws RegraDeNegocioException {
        return cupomRepository.findById(idCupom)
                .orElseThrow(() -> new RegraDeNegocioException("Cupom invalido!"));
    }

    private RelatorioCupomDTO converterEmDTOCupom(RelatorioCupom relatorioCupom) {
        return objectMapper.convertValue(relatorioCupom, RelatorioCupomDTO.class);
    }

    private CupomDTO converterEmDTO(CupomEntity cupomEntity) {
        return objectMapper.convertValue(cupomEntity, CupomDTO.class);
    }

    private List<CupomEntity> findAllCupomValido() {
        List<CupomEntity> cupomEntityList = cupomRepository.findAll();
        return cupomEntityList.stream()
                .filter(CupomEntity::isAtivo)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 * * * *")
    public void verificarCupons() {
        List<CupomEntity> cupomEntityList = findAllCupomValido();
        for (CupomEntity cupom : cupomEntityList) {
            String base = "";
            if (cupom.getDataVencimento().isBefore(LocalDate.now())) {
                cupom.setAtivo(false);
                cupomRepository.save(cupom);
                base = "Olá, seu cupom(" + cupom.getId() + "acaba de expirar!";
            } else if (cupom.getDataVencimento().isAfter(LocalDate.now())) {
                long tempo = LocalDate.now().until(cupom.getDataVencimento(), ChronoUnit.DAYS);
                base = "Olá, resta " + tempo + " dias para seu cupom expirar! O id do cupom é : " + cupom.getId();
            }
            emailService.sendEmail(base, cupom.getEmail());
        }
    }

}
