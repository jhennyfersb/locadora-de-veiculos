package br.com.dbc.vemser.sistemaaluguelveiculos.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "funcionario")
public class FuncionarioEntity implements UserDetails {
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
    @Column(name = "senha")
    private String senha;

    //criar no banco de dados > string 'T' e 'F' ou 'S' e'N'
    //private String enabled="T";

    //private String expired="F";

    //private String locked="F";

    @Column(name= "id_cargo",insertable = false,updatable = false)
    private int idCargo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    private CargoEntity cargoEntity;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionarioEntity")
    private Set<LocacaoEntity> locacaoEntities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        // return expired=="F";
    }

    @Override
    public boolean isAccountNonLocked() {
//        return locked=="F";
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return true;
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return enabled=="T";
        return true;
    }
}
