package br.com.lucasdemet.mydebts.repository;

import br.com.lucasdemet.mydebts.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByUsuarioId(Long usuarioId);           // contas de um usuário
    List<Conta> findByUsuarioIdAndPaga(Long usuarioId, boolean paga); // filtradas por status
    List<Conta> findByUsuarioIdOrderByVencimentoAsc(Long usuarioId); // todas as contas, vencimento crescente
    List<Conta> findByUsuarioIdAndPagaOrderByVencimentoAsc(Long usuarioId, boolean paga); // filtradas por status
    List<Conta> findByUsuarioIdAndVencimentoBetweenOrderByVencimentoAsc(Long usuarioId, LocalDate inicio, LocalDate fim);

}
