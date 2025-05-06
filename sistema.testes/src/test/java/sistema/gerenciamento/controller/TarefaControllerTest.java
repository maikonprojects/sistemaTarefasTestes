package sistema.gerenciamento.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import sistema.gerenciamento.modelo.Status;
import sistema.gerenciamento.modelo.Task;
import sistema.gerenciamento.servico.TaskServico;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskServico servico;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void cadastrar() throws Exception {

        Task task = new Task();
        task.setTitle("Test title");
        task.setDescription("Test desc");
        task.setStatus(Status.CONCLUIDA);

        mockMvc.perform(post("/tarefa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.description").value("Test desc"));
    }

    @Test
    void listar() throws Exception{
        Task task = new Task();

        task.setTitle("Adeus");
        task.setDescription("AAAAAAAAA");
        task.setStatus(Status.CONCLUIDA);
        servico.cadastrarTarefa(task);


        mockMvc.perform(get("/tarefa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Adeus"));

    }

    @Test
    void buscarPorId() throws Exception{

        Task task = new Task();

        task.setTitle("Adeus");
        task.setDescription("AAAAAAAAA");
        task.setStatus(Status.CONCLUIDA);

        servico.cadastrarTarefa(task);

        mockMvc.perform(get("/tarefa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Adeus"));
    }

    @Test
    void atualizar() throws Exception{
        Task task = new Task();

        task.setTitle("Solutis");
        task.setDescription("A");
        task.setStatus(Status.CONCLUIDA);
        servico.cadastrarTarefa(task);

        Task taskAtualizada = new Task();
        taskAtualizada.setId(1);
        taskAtualizada.setTitle("Casa");
        taskAtualizada.setDescription("Morar");
        taskAtualizada.setStatus(Status.CONCLUIDA);


        mockMvc.perform(put("/tarefa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Casa"))
                .andExpect(jsonPath("$.description").value("Morar"));

    }

    @Test
    void deletar() throws Exception{
        Task task = new Task();

        task.setTitle("Solutis");
        task.setDescription("A");
        task.setStatus(Status.CONCLUIDA);
        servico.cadastrarTarefa(task);


        mockMvc.perform(delete("/tarefa/1"))
                .andExpect(status().isOk());
    }
}