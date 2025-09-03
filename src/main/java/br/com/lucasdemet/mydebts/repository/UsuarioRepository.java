package br.com.lucasdemet.mydebts.repository;

import br.com.lucasdemet.mydebts.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca usuário pelo email (útil para login)
    Optional<Usuario> findByEmail(String email);

    // Verifica se já existe usuário com determinado email
    boolean existsByEmail(String email);
}
