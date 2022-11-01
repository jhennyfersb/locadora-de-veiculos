package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "contato")
public class ContatoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_CONTATO")
    @SequenceGenerator(name = "SEQ_CONTATO",sequenceName = "seq_contato",allocationSize = 1)
    @Column(name = "id_contato")
    private Integer idContato;
    @Column(name = "id_cliente")
    private Integer idCliente;
    private String telefone;
    private String email;
}