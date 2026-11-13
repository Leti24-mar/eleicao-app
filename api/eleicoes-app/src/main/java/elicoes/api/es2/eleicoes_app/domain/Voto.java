package elicoes.api.es2.eleicoes_app.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "eleicao_id")
    private Eleicao eleicao;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

}
