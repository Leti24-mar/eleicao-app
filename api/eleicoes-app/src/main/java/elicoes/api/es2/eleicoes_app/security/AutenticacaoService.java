package elicoes.api.es2.eleicoes_app.security;

import elicoes.api.es2.eleicoes_app.domain.Usuario;
import elicoes.api.es2.eleicoes_app.repository.UsuarioRepository;
import elicoes.api.es2.eleicoes_app.validation.CpfUtils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) {
        String normalizado = CpfUtils.somenteDigitos(cpf);
        Usuario usuario = usuarioRepository.findByCpf(normalizado == null ? "" : normalizado)
                .filter(encontrado -> encontrado.getCargo() != null && encontrado.getSenha() != null)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));
        return new UsuarioAutenticado(usuario);
    }

}
