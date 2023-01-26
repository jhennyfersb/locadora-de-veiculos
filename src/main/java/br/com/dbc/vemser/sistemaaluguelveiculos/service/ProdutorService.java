package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.dto.CupomCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.dto.LogCreateDTO;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutorService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final int VALIDADE_CUPOM = 30;

    private final double PORCENTAGEM_DESCONTO = 10.0;

    @Value("${kafka.topic}")
    private String topic;

    private final LogService logService;

    public void enviarMensagem(String email) throws JsonProcessingException {
        CupomCreateDTO cupomCreateDTO = new CupomCreateDTO();
        cupomCreateDTO.setAtivo(true);
        cupomCreateDTO.setEmail(email);
        cupomCreateDTO.setDesconto(PORCENTAGEM_DESCONTO);
        cupomCreateDTO.setDataCriacao(LocalDate.now());
        cupomCreateDTO.setDataVencimento(LocalDate.now().plusDays(VALIDADE_CUPOM));

        String mensagemStr = objectMapper.writeValueAsString(cupomCreateDTO);
        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagemStr)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .setHeader(KafkaHeaders.PARTITION_ID, 1);

        Message<String> message = stringMessageBuilder.build();
        ListenableFuture<SendResult<String, String>> enviadoParaTopico = kafkaTemplate.send(message);
        enviadoParaTopico.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                String cpf = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
                logService.salvarLog(new LogCreateDTO(TipoLog.CREATE, " CPF logado: " + cpf, EntityLog.CUPOM));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(" Erro ao publicar duvida no kafka com a mensagem");
            }
        });
    }

}

