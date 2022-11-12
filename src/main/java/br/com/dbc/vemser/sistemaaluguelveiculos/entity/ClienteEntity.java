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
@JsonIgnoreProperties
@Entity(name = "cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE")
    @SequenceGenerator(name = "SEQ_CLIENTE", sequenceName = "seq_cliente", allocationSize = 1)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nome_cliente")
    private String nome;

    @Column(name = "cpf_cliente")
    private String cpf;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteEntity", cascade = CascadeType.ALL)
    private Set<ContatoEntity> contatoEntities;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteEntity", cascade = CascadeType.ALL)
    private Set<EnderecoEntity> enderecoEntities;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteEntity", cascade = CascadeType.ALL)
    private Set<LocacaoEntity> locacaoEntities;


}
