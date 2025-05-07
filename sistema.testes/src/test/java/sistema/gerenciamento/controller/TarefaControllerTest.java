package sistema.gerenciamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import sistema.gerenciamento.modelo.Status;
import sistema.gerenciamento.modelo.Task;
import sistema.gerenciamento.servico.TaskServico;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskServico servico;

    @Autowired
    private ObjectMapper objectMapper;




    @Test
    void cadastrar() throws Exception {

        Task task = new Task();
        task.setTitle("Test title");
        task.setDescription("Test desc");
        task.setStatus(Status.CONCLUIDA);

        // Mock do serviço
        when(servico.cadastrarTarefa(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tarefa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.description").value("Test desc"));
    }

    @Test
    void buscarPorId() throws Exception{

        Task task = new Task();
        task.setTitle("Adeus");
        task.setDescription("AAAAAAAAA");
        task.setStatus(Status.CONCLUIDA);

        when(servico.buscarId(eq(1))).thenReturn(Optional.of(task));

        mockMvc.perform(get("/tarefa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Adeus"));
    }


    @Test
    void listar() throws Exception{
        Task task = new Task();

        task.setTitle("Adeus");
        task.setDescription("AAAAAAAAA");
        task.setStatus(Status.CONCLUIDA);
        when(servico.listar()).thenReturn(List.of(task));


        mockMvc.perform(get("/tarefa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Adeus"));

    }



    @Test
    void atualizar() throws Exception{
        Task task = new Task();

//        task.setTitle("Solutis");
//        task.setDescription("A");
//        task.setStatus(Status.CONCLUIDA);
//        servico.cadastrarTarefa(task);

        Task taskAtualizada = new Task();
        taskAtualizada.setId(1);
        taskAtualizada.setTitle("Casa");
        taskAtualizada.setDescription("Morar");
        taskAtualizada.setStatus(Status.CONCLUIDA);

        when(servico.atualizarId(eq(1),any(Task.class))).thenReturn(taskAtualizada);


        mockMvc.perform(put("/tarefa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Casa"))
                .andExpect(jsonPath("$.description").value("Morar"));

    }

    @Test
    void deletar() throws Exception{
        doNothing().when(servico).deletarPorId(1);

        mockMvc.perform(delete("/tarefa/1"))
                .andExpect(status().isOk());
    }
}