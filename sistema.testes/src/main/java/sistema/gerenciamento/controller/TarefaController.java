package sistema.gerenciamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.gerenciamento.modelo.Tarefa;
import sistema.gerenciamento.servico.TarefaServico;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Tarefa>> buscarPorId(@PathVariable Integer id){
        Optional<Tarefa> tarefa = servico.buscarId(id);
        return ResponseEntity.status(200).body(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Integer id, @RequestBody Tarefa tarefa){
        Tarefa tarefaAtualizada = servico.atualizarId(id,tarefa);
        return ResponseEntity.status(200).body(tarefaAtualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Tarefa> deletar(@PathVariable Integer id){
        servico.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }




}
