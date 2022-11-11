package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

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

    @Override
    public String getAuthority() {
        return nome;
    }
}
