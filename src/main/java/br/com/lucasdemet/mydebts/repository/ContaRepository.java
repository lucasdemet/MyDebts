package br.com.lucasdemet.mydebts.repository;

import br.com.lucasdemet.mydebts.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByUsuarioId(Long usuarioId);           // contas de um usuário
    List<Conta> findByUsuarioIdAndPaga(Long usuarioId, boolean paga); // filtradas por status
}
