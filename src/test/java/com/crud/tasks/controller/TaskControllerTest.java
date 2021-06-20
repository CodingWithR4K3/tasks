package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldFetchEmptyTaskLists() throws Exception {

        //Given
        List<Task> testList = new ArrayList<>();
        List<TaskDto> testListDto = new ArrayList<>();

        when(dbService.getAllTasks()).thenReturn(testList);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(testListDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTaskLists() throws Exception {

        //Given
        List<TaskDto> testListDto = new ArrayList<>();
        testListDto.add(new TaskDto(1L, "test1", "test1"));
        testListDto.add(new TaskDto(2L, "test2", "test2"));
        testListDto.add(new TaskDto(3L, "test3", "test3"));

        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(testListDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("test1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("test1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("test2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("test2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title", Matchers.is("test3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].content", Matchers.is("test3")));
    }

    @Test
    void shouldDeleteTask() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/tasks/1")
                .param("taskId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(1L, "test", "test");
        TaskDto updatedTask = new TaskDto(1L, "update", "update");
        when(taskMapper.mapToTaskDto(taskMapper.mapToTask(taskDto))).thenReturn(updatedTask);

        Gson gson = new Gson();
        String toJson = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/tasks").
                contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(toJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("update")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("update")));
    }

    @Test
    void shouldCreateTask() throws Exception {

        //Given
        Task task = new Task(1L, "test", "test 1");
        TaskDto taskDto = new TaskDto(1L, "test", "test 1");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String toJson = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/tasks").
                contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(toJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}