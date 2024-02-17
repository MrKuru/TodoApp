package com.hb.mappers;


import com.hb.dto.responses.TaskViewResponse;
import com.hb.model.documents.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    public TaskViewResponse toView(Task task) {
        return TaskViewResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .todoId(task.getTodoId())
                .build();
    }
    public TaskViewResponse optionalToView(Optional<Task> optionalTask) {
        return TaskViewResponse.builder()
                .id(optionalTask.get().getId())
                .title(optionalTask.get().getTitle())
                .description(optionalTask.get().getDescription())
                .todoId(optionalTask.get().getTodoId())
                .build();
    }

    public List<TaskViewResponse> toViewList(List<Task> taskList) {
        return taskList.stream()
                .map(this::toView)
                .collect(Collectors.toList());
    }
}
