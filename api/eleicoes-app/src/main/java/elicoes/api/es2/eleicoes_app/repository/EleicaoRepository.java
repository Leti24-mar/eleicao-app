package elicoes.api.es2.eleicoes_app.repository;

import elicoes.api.es2.eleicoes_app.domain.Eleicao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EleicaoRepository extends JpaRepository<Eleicao, Long> {
}
