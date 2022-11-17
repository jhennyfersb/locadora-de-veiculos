package br.com.dbc.vemser.sistemaaluguelveiculos.entity;


import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.EntityLog;
import br.com.dbc.vemser.sistemaaluguelveiculos.entity.enums.TipoLog;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "log")
@Getter
@Setter
public class LogEntity {

    @Id
    private String id;

    private TipoLog tipoLog;

    private EntityLog entityLog;

    private String descricao;

    private String data;

}
