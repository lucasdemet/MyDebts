package br.com.lucasdemet.mydebts.service.impl;

import br.com.lucasdemet.mydebts.model.Conta;
import br.com.lucasdemet.mydebts.repository.ContaRepository;
import br.com.lucasdemet.mydebts.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    @Override
    public Conta salvar(Conta conta) {
        // Validações básicas
        if (conta.getValor() == null || conta.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da conta deve ser maior que zero.");
        }

        if (conta.getVencimento() == null) {
            throw new IllegalArgumentException("O vencimento da conta não pode ser nulo.");
        }

        if (conta.isParcelada() && (conta.getQuantidadeParcelas() == null || conta.getQuantidadeParcelas() <= 1)) {
            throw new IllegalArgumentException("Quantidade de parcelas deve ser maior que 1 para contas parceladas.");
        }

        // Caso não seja parcelada ou quantidade inválida
        if (!conta.isParcelada() || conta.getQuantidadeParcelas() <= 1) {
            conta.setPaga(false);
            return contaRepository.save(conta);
        }

        // Lógica de parcelamento
        final int n = conta.getQuantidadeParcelas();
        final BigDecimal total = conta.getValor();
        BigDecimal base = total.divide(BigDecimal.valueOf(n), 2, RoundingMode.DOWN);
        BigDecimal resto = total.subtract(base.multiply(BigDecimal.valueOf(n)));

        Conta primeiraParcelaSalva = null;

        for (int i = 1; i <= n; i++) {
            BigDecimal valorParcela = base;
            if (i == n) valorParcela = valorParcela.add(resto);

            Conta parcela = new Conta();
            parcela.setTitulo(conta.getTitulo() + " - Parcela " + i + "/" + n);
            parcela.setValor(valorParcela);
            parcela.setVencimento(conta.getVencimento().plusMonths(i - 1));
            parcela.setParcelada(true);
            parcela.setQuantidadeParcelas(n);
            parcela.setPaga(false);
            parcela.setUsuario(conta.getUsuario());

            Conta salva = contaRepository.save(parcela);
            if (i == 1) primeiraParcelaSalva = salva;
        }

        return primeiraParcelaSalva;
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
        return contaRepository.findByUsuarioIdOrderByVencimentoAsc(usuarioId);    }

    @Override
    public List<Conta> listarPorUsuarioEStatus(Long usuarioId, boolean paga) {
        return contaRepository.findByUsuarioIdAndPagaOrderByVencimentoAsc(usuarioId, paga);
    }

    public List<Conta> listarPorUsuarioPorPeriodo(Long usuarioId, LocalDate inicio, LocalDate fim) {
        return contaRepository.findByUsuarioIdAndVencimentoBetweenOrderByVencimentoAsc(usuarioId, inicio, fim);
    }

    @Override
    public void marcarComoPaga(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com id: " + id));

        conta.setPaga(true);
        contaRepository.save(conta);
    }


}
