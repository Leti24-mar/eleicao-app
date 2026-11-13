package elicoes.api.es2.eleicoes_app.domain;

import elicoes.api.es2.eleicoes_app.domain.Enum.CargoUsuario;

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

    private String nome;

    private String sobrenome;

    private String nomeDaMae;

    @Column(unique = true)
    private String cpf;

    private String telefone;

    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private CargoUsuario cargo;

}
