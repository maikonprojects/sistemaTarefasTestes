package sistema.gerenciamento.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistema.gerenciamento.modelo.Task;

@Repository
public interface TarefaRepositorio extends JpaRepository<Task, Integer> {
}
