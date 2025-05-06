package sistema.gerenciamento.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistema.gerenciamento.modelo.Tarefa;

@Repository
public interface TarefaRepositorio extends JpaRepository<Tarefa, Integer> {
}
