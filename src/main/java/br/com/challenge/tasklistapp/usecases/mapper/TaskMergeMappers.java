package br.com.challenge.tasklistapp.usecases.mapper;

import br.com.challenge.tasklistapp.domains.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TaskMergeMappers {

    Task mergerTask (Task taskToUpdate, @MappingTarget Task savedTask);

    enum TaskMergeMapper {
        ;
        public static final TaskMergeMappers MAPPERS = Mappers.getMapper(TaskMergeMappers.class);
    }
}
