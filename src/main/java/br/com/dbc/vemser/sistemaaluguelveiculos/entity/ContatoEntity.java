package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


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
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cliente", referencedColumnName = "id_cliente", insertable = false, updatable = false)
    private ClienteEntity clienteEntity;
}