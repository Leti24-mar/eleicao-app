package elicoes.api.es2.eleicoes_app.domain;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "eleicao_id"}),
        @UniqueConstraint(columnNames = {"eleicao_id", "numero"})
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "eleicao_id")
    private Eleicao eleicao;

    private String numero;

    private String partido;

    @Enumerated(EnumType.STRING)
    private TipoCandidato tipo;

}
