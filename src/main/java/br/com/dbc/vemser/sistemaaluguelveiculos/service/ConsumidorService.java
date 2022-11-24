package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CupomDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumidorService {

    private final ObjectMapper objectMapper;
    private final CupomService cupomService;


    @KafkaListener(
            clientIdPrefix = "${spring.kafka.consumer.client-id}",
            groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"0"})}
    )
    public void consumirMensagensIndividuais(@Payload String mensagem) throws JsonProcessingException {
        CupomDTO cupomDTO = objectMapper.readValue(mensagem, CupomDTO.class);
        cupomService.save(cupomDTO);
    }
}
