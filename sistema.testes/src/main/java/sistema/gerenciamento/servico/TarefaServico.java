package sistema.gerenciamento.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.gerenciamento.modelo.Tarefa;
import sistema.gerenciamento.repositorio.TarefaRepositorio;

import java.util.List;

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
}
