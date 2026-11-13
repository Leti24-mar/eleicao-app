package elicoes.api.es2.eleicoes_app.repository;

import elicoes.api.es2.eleicoes_app.domain.Candidato;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    boolean existsByEleicaoIdAndNumero(Long eleicaoId, String numero);

    boolean existsByEleicaoIdAndNumeroAndIdNot(Long eleicaoId, String numero, Long id);

    boolean existsByUsuarioIdAndEleicaoIdAndIdNot(Long usuarioId, Long eleicaoId, Long id);

    boolean existsByUsuarioId(Long usuarioId);

}
