package br.com.lucasdemet.mydebts.controller;

import br.com.lucasdemet.mydebts.model.Conta;
import br.com.lucasdemet.mydebts.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    // Criar ou atualizar conta
    @PostMapping
    public ResponseEntity<Conta> criarOuAtualizar(@Valid @RequestBody Conta conta) {
        Conta salva = contaService.salvar(conta);
        return ResponseEntity.ok(salva);
    }

    // Listar todas as contas de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Conta>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(contaService.listarPorUsuario(usuarioId));
    }

    // Listar contas por status
    @GetMapping("/usuario/{usuarioId}/status")
    public ResponseEntity<List<Conta>> listarPorStatus(
            @PathVariable Long usuarioId,
            @RequestParam boolean paga) {
        return ResponseEntity.ok(contaService.listarPorUsuarioEStatus(usuarioId, paga));
    }

    // Buscar conta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar conta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
