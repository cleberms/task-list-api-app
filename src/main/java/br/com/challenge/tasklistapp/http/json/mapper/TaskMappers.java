package br.com.challenge.tasklistapp.http.json.mapper;

import br.com.challenge.tasklistapp.domains.Task;
import br.com.challenge.tasklistapp.http.json.TaskVO;
import br.com.challenge.tasklistapp.http.json.TaskVORequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TaskMappers {

    List<TaskVO> listTaskToVO(final List<Task> taskList);

    TaskVO taskToVO (final Task task);

    Task taskRequestToDomain (final TaskVORequest request);

    enum TaskMapper {
        ;
        public static final TaskMappers MAPPER = Mappers.getMapper(TaskMappers.class);
    }
}
