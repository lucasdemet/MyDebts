package br.com.lucasdemet.mydebts.service;

import br.com.lucasdemet.mydebts.model.Conta;

import java.util.List;
import java.util.Optional;

public interface ContaService {
    Conta salvar(Conta conta);            // criar ou atualizar conta
    Optional<Conta> buscarPorId(Long id);
    void deletar(Long id);
    List<Conta> listarPorUsuario(Long usuarioId);
    List<Conta> listarPorUsuarioEStatus(Long usuarioId, boolean paga);
}
