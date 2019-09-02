package br.com.challenge.tasklistapp.usecases.mapper;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.usecases.mapper.TaskMergeMappers.TaskMergeMapper;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskMergeMappersUnitTest {

    @Test
    public void shouldMerdeTasks() {
        final Task savedTask = new Task("121",5145L, LocalDateTime.now(),LocalDateTime.now(), "Create Teste", "Test gateway implementation",TaskStatus.TO_DO,"Cleber Santaterra", null);

        final Task taskToUpdate = new Task();
        taskToUpdate.setStatus(TaskStatus.WIP);
        taskToUpdate.setAssignedName("Cleber Santaterra");

        Task result = TaskMergeMapper.MAPPERS.mergerTask(taskToUpdate, savedTask);

        assertNotNull(result);

        assertEquals(TaskStatus.WIP, taskToUpdate.getStatus());
        assertEquals("Cleber Santaterra", taskToUpdate.getAssignedName());

        assertEquals(result.getUid(), savedTask.getUid());
        assertEquals(result.getCreatedAt(), savedTask.getCreatedAt());
        assertEquals(result.getTaskId(), savedTask.getTaskId());
        assertEquals(result.getUpdateAt(), savedTask.getUpdateAt());
        assertEquals(result.getName(), savedTask.getName());
        assertEquals(result.getDescription(), savedTask.getDescription());
        assertEquals(result.getReporterName(), savedTask.getReporterName());
    }
}
