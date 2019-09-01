package br.com.challenge.tasklistapp.http.json;

import br.com.challenge.tasklistapp.domains.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskVORequest {

    @NotBlank
    private String name;

    private String description;

    private TaskStatus status;

    @NotBlank
    private String reporterName;

    private String assignedName;
}
