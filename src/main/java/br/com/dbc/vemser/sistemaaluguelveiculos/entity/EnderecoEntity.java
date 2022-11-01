package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "endereco_cliente")
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_ENDERECO")
    @SequenceGenerator(name = "SEQ_ENDERECO",sequenceName = "seq_endereco_cliente",allocationSize = 1)
    @Column(name = "id_endereco")
    private Integer idEndereco;
    @Column(name = "id_cliente")
    private Integer idCliente;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
}
