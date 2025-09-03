package br.com.lucasdemet.mydebts.service;

import br.com.lucasdemet.mydebts.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario cadastrar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    List<Usuario> listarTodos();         // NOVO

    Optional<Usuario> buscarPorId(Long id); // NOVO

    void deletar(Long id);               // NOVO
}
