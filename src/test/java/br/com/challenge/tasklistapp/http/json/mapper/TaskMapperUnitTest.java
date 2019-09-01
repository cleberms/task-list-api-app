package br.com.challenge.tasklistapp.http.json.mapper;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import br.com.challenge.tasklistapp.http.json.TaskVO;
import br.com.challenge.tasklistapp.http.json.TaskVORequest;
import br.com.challenge.tasklistapp.http.json.mapper.TaskMappers.TaskMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskMapperUnitTest {


    @Test
    public void shouldMapperListSuccessfully() {

        List<Task> taskListDomain = getTasks();

        List<TaskVO> taskListVO = TaskMapper.MAPPER.listTaskToVO(taskListDomain);

        assertNotNull(taskListVO);
        assertEquals(2, taskListVO.size());

        assertTask(taskListDomain.get(0), taskListVO.get(0));
        assertTask(taskListDomain.get(1), taskListVO.get(1));
    }

    @Test
    public void shouldMapperDomainToVOSuccessfully() {
        List<Task> taskListDomain = getTasks();

        TaskVO taskVO = TaskMapper.MAPPER.taskToVO(taskListDomain.get(0));

        assertNotNull(taskVO);

        assertTask(taskListDomain.get(0), taskVO);
    }

    @Test
    public void shouldMapperVORequestoToDomainSuccessfully() {
        TaskVORequest taskVORequest = new TaskVORequest("Task Name", "Task Description", TaskStatus.WIP, "Report Name", "Assigned Name");

        Task task = TaskMapper.MAPPER.taskRequestToDomain(taskVORequest);

        assertNotNull(task);

        assertEquals(task.getName(), taskVORequest.getName());
        assertEquals(task.getDescription(), taskVORequest.getDescription());
        assertEquals(task.getReporterName(), taskVORequest.getReporterName());
        assertEquals(task.getAssignedName(), taskVORequest.getAssignedName());
    }

    private List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task("121",5014L, null,null, "Create Teste", "Test gateway implementation",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        taskList.add(task);

        task = new Task("122",5015L, null,null, "Create Teste", "Test gateway implementation to Repo",TaskStatus.WIP,"Cleber Santaterra", "Cleber Santaterra");

        taskList.add(task);

        return taskList;
    }

    private void assertTask(final Task expected, final TaskVO result) {

        assertEquals(expected.getAssignedName(), result.getAssignedName());
        assertEquals(expected.getCreatedAt(), result.getCreatedAt());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getReporterName(), result.getReporterName());
        assertEquals(expected.getStatus(), result.getStatus());
        assertEquals(expected.getTaskId(), result.getTaskId());
        assertEquals(expected.getUpdateAt(), result.getUpdateAt());
        assertEquals(expected.getName(), result.getName());
    }
}