package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_CLIENTE")
    @SequenceGenerator(name = "SEQ_CLIENTE",sequenceName = "seq_cliente",allocationSize = 1)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "nome_cliente")
    private String nome;
    @Column(name = "cpf_cliente")
    private String cpf;
}
