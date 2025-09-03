package br.com.lucasdemet.mydebts.service.impl;

import br.com.lucasdemet.mydebts.model.Conta;
import br.com.lucasdemet.mydebts.repository.ContaRepository;
import br.com.lucasdemet.mydebts.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    @Override
    public Conta salvar(Conta conta) {
        // Se for parcelada, podemos gerar parcelas lógicas aqui (opcional)
        return contaRepository.save(conta);
    }

    @Override
    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.findById(id);
    }

    @Override
    public void deletar(Long id) {
        contaRepository.deleteById(id);
    }

    @Override
    public List<Conta> listarPorUsuario(Long usuarioId) {
        return contaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Conta> listarPorUsuarioEStatus(Long usuarioId, boolean paga) {
        return contaRepository.findByUsuarioIdAndPaga(usuarioId, paga);
    }
}
