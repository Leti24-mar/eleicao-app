package elicoes.api.es2.eleicoes_app.domain;

import elicoes.api.es2.eleicoes_app.domain.Enum.CargoUsuario;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(length = 80)
    private String sobrenome;

    @Column(length = 80)
    private String nomeDaMae;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(length = 11)
    private String telefone;

    @Column(unique = true, length = 120)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 72)
    private String senha;

    @Enumerated(EnumType.STRING)
    private CargoUsuario cargo;

}
