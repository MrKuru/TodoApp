package com.hb.controllers;


import com.hb.components.TaskComponent;
import com.hb.dto.requests.CreateTaskRequest;
import com.hb.dto.responses.TaskViewResponse;
import com.hb.services.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/1.0/task")
@SecurityRequirement(name = "basicAuth")
public class TaskController implements TaskService {
    private final TaskComponent taskComponent;

    @Override
    public TaskViewResponse createTask(CreateTaskRequest request) {
        TaskViewResponse response = taskComponent.createTask(request);
        return response;
    }

    @Override
    public TaskViewResponse getTaskById(Long id) {
        TaskViewResponse response = taskComponent.getTaskById(id);
        return response;
    }

    @Override
    public List<TaskViewResponse> getTasksByTodoId(Long todoId) {
        List<TaskViewResponse> responseList = taskComponent.getTasksByTodoId(todoId);
        return responseList;
    }

    @Override
    public void deleteTask(Long id) {
        taskComponent.deleteTask(id);
    }
}
