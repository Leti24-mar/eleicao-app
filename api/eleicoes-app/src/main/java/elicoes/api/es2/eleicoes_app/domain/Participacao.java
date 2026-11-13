package elicoes.api.es2.eleicoes_app.domain;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "eleicao_id", "tipo"}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario eleitor;

    @ManyToOne
    @JoinColumn(name = "eleicao_id")
    private Eleicao eleicao;

    @Enumerated(EnumType.STRING)
    private TipoCandidato tipo;

}
