package br.com.dbc.vemser.sistemaaluguelveiculos.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRT")
    @SequenceGenerator(name = "SEQ_PRT", sequenceName = "SEQ_PRT", allocationSize = 1)
    @Column(name = "id_password_reset_token")
    private Long id;

    private String token;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_funcionario")
    private FuncionarioEntity funcionarioEntity;

    public PasswordResetToken(String token, FuncionarioEntity funcionarioEntity) {
        this.token = token;
        this.funcionarioEntity = funcionarioEntity;
    }
}
