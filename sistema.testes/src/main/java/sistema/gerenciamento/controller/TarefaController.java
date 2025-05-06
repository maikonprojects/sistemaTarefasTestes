package sistema.gerenciamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.gerenciamento.modelo.Task;
import sistema.gerenciamento.servico.TaskServico;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    TaskServico servico;

    @PostMapping
    public ResponseEntity<Task> cadastrar(@RequestBody Task dados){
        return ResponseEntity.status(201).body(servico.cadastrarTarefa(dados));
    }

    @GetMapping
    public ResponseEntity<List<Task>> listar(){
        return ResponseEntity.status(200).body(servico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> buscarPorId(@PathVariable Integer id){
        Optional<Task> tarefa = servico.buscarId(id);
        return ResponseEntity.status(200).body(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> atualizar(@PathVariable Integer id, @RequestBody Task tarefa){
        Task tarefaAtualizada = servico.atualizarId(id,tarefa);
        return ResponseEntity.status(200).body(tarefaAtualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deletar(@PathVariable Integer id){
        servico.deletarPorId(id);
        return ResponseEntity.status(200).build();
    }




}
