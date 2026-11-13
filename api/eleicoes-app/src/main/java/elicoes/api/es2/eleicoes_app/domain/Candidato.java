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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "eleicao_id", nullable = false)
    private Eleicao eleicao;

    @Column(nullable = false, length = 5)
    private String numero;

    @Column(nullable = false, length = 80)
    private String partido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoCandidato tipo;

}
