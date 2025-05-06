package sistema.gerenciamento.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sistema.gerenciamento.modelo.Tarefa;
import sistema.gerenciamento.repositorio.TarefaRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaServico {

    @Autowired
    TarefaRepositorio repositorio;

    public Tarefa cadastrarTarefa(Tarefa dados){
        return repositorio.save(dados);
    }

    public List<Tarefa> listar() {
        return repositorio.findAll();
    }

    public Optional<Tarefa> buscarId(Integer id){
        Optional<Tarefa> tarefa = repositorio.findById(id);
        return tarefa;
    }

    public Tarefa atualizarId(Integer id, Tarefa tarefa){

           Tarefa tarefaEncontrada = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

            tarefaEncontrada.setId(id);
            tarefaEncontrada.setDescricao(tarefa.getDescricao());
            tarefaEncontrada.setTitulo(tarefa.getTitulo());
            tarefaEncontrada.setStatus(tarefa.getStatus());

            return repositorio.save(tarefaEncontrada);

    }

    public void deletarPorId(Integer id){
        Tarefa tarefa = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        repositorio.deleteById(id);
    }


}
