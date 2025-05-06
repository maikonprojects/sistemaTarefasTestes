package sistema.gerenciamento.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.gerenciamento.modelo.Status;
import sistema.gerenciamento.modelo.Task;
import sistema.gerenciamento.repositorio.TarefaRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaServico {

    @Autowired
    TarefaRepositorio repositorio;

    public Task cadastrarTarefa(Task dados){

        Status status = dados.getStatus();
        if (status != Status.CONCLUIDA && status != Status.EM_ANDAMENTO && status != Status.PENDENTE) {
            throw new RuntimeException("Status não está correto");
        }

        return repositorio.save(dados);

    }

    public List<Task> listar() {
        return repositorio.findAll();
    }

    public Optional<Task> buscarId(Integer id){
        Optional<Task> tarefa = repositorio.findById(id);
        return tarefa;
    }

    public Task atualizarId(Integer id, Task tarefa){

           Task tarefaEncontrada = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

            tarefaEncontrada.setId(id);
            tarefaEncontrada.setDescription(tarefa.getDescription());
            tarefaEncontrada.setTitle(tarefa.getTitle());
            tarefaEncontrada.setStatus(tarefa.getStatus());

            return repositorio.save(tarefaEncontrada);

    }

    public void deletarPorId(Integer id){
        Task tarefa = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        repositorio.deleteById(id);
    }


}
