package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "funcionario")
public class FuncionarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_FUNCIONARIO")
    @SequenceGenerator(name = "SEQ_FUNCIONARIO",sequenceName = "seq_funcionario",allocationSize = 1)
    @Column(name = "id_funcionario")
    private Integer idFuncionario;
    @Column(name = "nome_funcionario")
    private String nome;
    @Column(name = "cpf_funcionario")
    private String cpf;
    @Column(name = "email_funcionario")
    private String email;
    private Integer matricula;
}
