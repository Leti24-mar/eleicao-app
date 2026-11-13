package elicoes.api.es2.eleicoes_app.domain;

import elicoes.api.es2.eleicoes_app.domain.Enum.StatusEleicao;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Eleicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private Integer turno;

    private LocalTime horarioAbertura;

    private LocalTime horarioEncerramento;

    @Enumerated(EnumType.STRING)
    private StatusEleicao status;

}
