package sistema.gerenciamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.gerenciamento.modelo.Tarefa;
import sistema.gerenciamento.servico.TarefaServico;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    TarefaServico servico;

    @PostMapping
    public ResponseEntity<Tarefa> cadastrar(@RequestBody Tarefa dados){
        return ResponseEntity.status(201).body(servico.cadastrarTarefa(dados));
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listar(){
        return ResponseEntity.status(200).body(servico.listar());
    }

}
