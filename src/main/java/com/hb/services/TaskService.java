package com.hb.services;


import com.hb.dto.requests.CreateTaskRequest;
import com.hb.dto.responses.TaskViewResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
public interface TaskService {
    @PostMapping("/task")
    TaskViewResponse createTask(@RequestBody CreateTaskRequest request);
    @GetMapping("/task/{id}")
    TaskViewResponse getTaskById(@PathVariable Long id);
    @GetMapping("/task/todo/{todoId}")
    List<TaskViewResponse> getTasksByTodoId(@PathVariable Long todoId);
    @DeleteMapping("/task/{id}")
    void deleteTask(@PathVariable Long id);
}
