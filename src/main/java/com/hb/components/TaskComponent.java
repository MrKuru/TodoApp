package com.hb.components;


import com.hb.dto.requests.CreateTaskRequest;
import com.hb.dto.responses.TaskViewResponse;
import com.hb.exception.exceptions.TaskNotFoundException;
import com.hb.mappers.TaskMapper;
import com.hb.model.documents.Task;
import com.hb.model.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskComponent {
    private final TaskRepository repository;
    private final TaskMapper mapper;
    public TaskViewResponse createTask(CreateTaskRequest request) {
        Task task = Task.builder()
                .id(request.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .todoId(request.getTodoId())
                .build();
        repository.save(task);
        return mapper.toView(task);
    }

    public TaskViewResponse getTaskById(Long id) {
        Optional<Task> optionalTask = repository.findById(id);
        if (optionalTask.isEmpty()){
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        return mapper.optionalToView(optionalTask);
    }

    public List<TaskViewResponse> getTasksByTodoId(Long todoId) {
        List<Task> taskList = repository.findByTodoId(todoId);
        return mapper.toViewList(taskList);
    }

    public void deleteTask(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
    }
}
