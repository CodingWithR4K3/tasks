package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void testGetAllTasks() {

        //Given
        Task task1 = new Task(1L, "test", "test");
        Task task2 = new Task(2L, "test", "test");
        Task task3 = new Task(3L, "test", "test");
        List<Task> testList = new ArrayList<>();
        testList.add(task1);
        testList.add(task2);

        when(taskRepository.findAll()).thenReturn(testList);

        //When
        List<Task> testTaskList = dbService.getAllTasks();

        //Then
        assertEquals(2, testTaskList.size());
    }

    @Test
    public void testGetAllTasksWithEmptyList() {

        //Given
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<Task> testList = dbService.getAllTasks();

        //Then
        assertEquals(0, testList.size());
    }

    @Test
    public void testGetTaskById() {

        //Given
        Task task = new Task(1L, "test", "test");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //When
        Optional<Task> testId = dbService.getTask(1L);
        long idTest = testId.orElseThrow().getId();

        //Then
        assertEquals(task.getId(), idTest);
    }

    @Test
    public void testSaveTask() {

        //Given
        Task task = new Task(1L, " test", "test");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task testTask = dbService.saveTask(task);

        //Then
        assertEquals(task.getId(), testTask.getId());
        assertEquals(task.getTitle(), testTask.getTitle());
        assertEquals(task.getContent(), testTask.getContent());
    }

    @Test
    public void testGetTask() {

        //Given
        Task task = new Task(1L, "test", "test");
        when(dbService.getTask(1L)).thenReturn(Optional.of(task));

        //When
        Optional<Task> testId = dbService.getTask(1L);

        //Then
        assertTrue(testId.isPresent());
        assertEquals(task.getId(), testId.get().getId());
    }

    @Test
    public void testDeleteTask() {

        //Given
        Task task = new Task(1L, "test", "test");
        dbService.saveTask(task);

        //When
        dbService.deleteTask(task.getId());

        //Then
        assertTrue(dbService.getTask(task.getId()).isEmpty());
    }
}