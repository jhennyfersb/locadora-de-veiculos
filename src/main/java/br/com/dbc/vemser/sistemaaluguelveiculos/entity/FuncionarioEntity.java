package br.com.dbc.vemser.sistemaaluguelveiculos.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "funcionario")
public class FuncionarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FUNCIONARIO")
    @SequenceGenerator(name = "SEQ_FUNCIONARIO", sequenceName = "seq_funcionario", allocationSize = 1)
    @Column(name = "id_funcionario")
    private Integer idFuncionario;

    @Column(name = "nome_funcionario")
    private String nome;

    @Column(name = "cpf_funcionario")
    private String cpf;

    @Column(name = "email_funcionario")
    private String email;

    @Column(name = "matricula")
    private Integer matricula;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionarioEntity")
    private Set<LocacaoEntity> locacaoEntities;

}
