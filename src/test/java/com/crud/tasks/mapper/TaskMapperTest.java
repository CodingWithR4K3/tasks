package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

    @InjectMocks
    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    public void mapToTask() {

        // Given
        TaskDto taskDto = new TaskDto(1L, "test", "test");

        // When
        Task testTask = taskMapper.mapToTask(taskDto);
        Long idTest = 1L;

        //Then
        assertEquals(idTest, testTask.getId());
        assertEquals("test", testTask.getTitle());
        assertEquals("test", testTask.getContent());
    }

    @Test
    public void mapToTaskDto() {

        // Given
        Task task = new Task(1L, "test", "test");

        // When
        TaskDto testTaskDto = taskMapper.mapToTaskDto(task);
        Long expectedId = 1L;

        //Then
        assertEquals(expectedId, testTaskDto.getId());
        assertEquals("test", testTaskDto.getTitle());
        assertEquals("test", testTaskDto.getContent());
    }

    @Test
    public void mapToTaskDtoList() {
        // Given
        Task task = new Task(1L, "test", "test");
        List<Task> testList = new ArrayList<>();
        testList.add(task);

        // When
        List<TaskDto> testTaskDtoList = taskMapper.mapToTaskDtoList(testList);
        Long expectedId = 1L;
        List<TaskDto> testEmptyTaskDtoList = taskMapper.mapToTaskDtoList(new ArrayList<>());

        //Then
        assertEquals(expectedId, testTaskDtoList.get(0).getId());
        assertEquals("test", testTaskDtoList.get(0).getTitle());
        assertEquals("test", testTaskDtoList.get(0).getContent());
        assertEquals(1, testTaskDtoList.size());
        assertEquals(0, testEmptyTaskDtoList.size());
    }
}