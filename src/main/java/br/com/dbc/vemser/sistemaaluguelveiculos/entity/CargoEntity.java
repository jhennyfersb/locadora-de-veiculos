package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CARGO")
public class CargoEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQUENCIA")
    @SequenceGenerator(name = "CARGO_SEQUENCIA", sequenceName = "seq_cargo", allocationSize = 1)
    @Column(name = "ID_CARGO")
    private int idCargo;

    @Column(name = "NOME")
    private String nome;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cargoEntity")
    private Set<FuncionarioEntity> funcionariosEntities;


    @Override
    public String getAuthority() {
        return nome;
    }
}
