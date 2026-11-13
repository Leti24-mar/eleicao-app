package elicoes.api.es2.eleicoes_app.repository;

import elicoes.api.es2.eleicoes_app.domain.Voto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    boolean existsByCandidatoId(Long candidatoId);

}
