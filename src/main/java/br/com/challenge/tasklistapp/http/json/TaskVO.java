package br.com.challenge.tasklistapp.http.json;

import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskVO implements Serializable {

    private Long  taskId;

    private LocalDateTime createdAt;

    @JsonInclude(Include.NON_NULL)
    private LocalDateTime updateAt;

    private String name;

    private String description;

    private TaskStatus status;

    private String reporterName;

    private String assignedName;

}
