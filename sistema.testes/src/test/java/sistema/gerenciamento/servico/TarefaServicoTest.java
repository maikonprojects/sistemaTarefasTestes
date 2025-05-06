package sistema.gerenciamento.servico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sistema.gerenciamento.modelo.Status;
import sistema.gerenciamento.modelo.Task;
import sistema.gerenciamento.repositorio.TarefaRepositorio;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServicoTest {

    @Mock
    private TarefaRepositorio repositorio;

    private Task task;

    @InjectMocks
    private TarefaServico servico;


    @BeforeEach
    void setUp(){
        task = new Task();
        task.setTitle("Alo");
        task.setDescription("Ler novamente");
        task.setStatus(Status.CONCLUIDA);
    }

    @Test
    void cadastrarTarefa() {
        Task entidadeSalva = new Task();
        entidadeSalva.setId(1);
        entidadeSalva.setTitle("Alo");
        entidadeSalva.setDescription("Ler novamente");
        entidadeSalva.setStatus(Status.CONCLUIDA);

        when(repositorio.save(any(Task.class))).thenReturn(entidadeSalva);

        Task eventoSalvo = servico.cadastrarTarefa(task);

        assertNotNull(eventoSalvo);
        assertEquals("Alo", eventoSalvo.getTitle());
        assertEquals(1, eventoSalvo.getId());
        assertEquals("Ler novamente", eventoSalvo.getDescription());
        assertEquals(Status.CONCLUIDA, eventoSalvo.getStatus());
        verify(repositorio, times(1)).save(any(Task.class));
    }

    @Test
    void listar() {
        when(repositorio.findAll()).thenReturn(Arrays.asList(task));

        var evento = servico.listar();

        assertEquals(1, evento.size());
        assertEquals("Alo", evento.get(0).getTitle());
        verify(repositorio, times(1)).findAll();
    }

    @Test
    void buscarId() {

        when(repositorio.findById(1)).thenReturn(Optional.of(task));

        Optional<Task> task = servico.buscarId(1);

        assertTrue(task.isPresent());
        assertEquals("Alo", task.get().getTitle());
        verify(repositorio, times(1)).findById(1);



    }

    @Test
    void atualizarId() {

        Task tarefaExistente = new Task();
        tarefaExistente.setId(1);
        tarefaExistente.setTitle("Alo");
        tarefaExistente.setDescription("Ler novamente");
        tarefaExistente.setStatus(Status.CONCLUIDA);

        Task novosDadosEtidade = new Task();
        novosDadosEtidade.setId(1);
        novosDadosEtidade.setTitle("Tchau");
        novosDadosEtidade.setDescription("Ler novamente");
        novosDadosEtidade.setStatus(Status.CONCLUIDA);

        when(repositorio.findById(1)).thenReturn(Optional.of(tarefaExistente));
        when(repositorio.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task eventoSalvo = servico.atualizarId(1, novosDadosEtidade);

        assertNotNull(eventoSalvo);
        assertEquals("Tchau", eventoSalvo.getTitle());
        assertEquals(1, eventoSalvo.getId());
        assertEquals("Ler novamente", eventoSalvo.getDescription());
        assertEquals(Status.CONCLUIDA, eventoSalvo.getStatus());
        verify(repositorio, times(1)).save(any(Task.class));
    }

    @Test
    void deletarPorId() {
        when(repositorio.findById(1)).thenReturn(Optional.of(task));

        servico.deletarPorId(1);
        verify(repositorio,times(1)).deleteById(1);


    }


}