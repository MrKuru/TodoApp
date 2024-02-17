package com.hb.controllers;


import com.hb.components.TodoComponent;
import com.hb.dto.requests.CreateTodoRequest;
import com.hb.dto.responses.TodoViewResponse;
import com.hb.services.TodoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/1.0/todo")
@SecurityRequirement(name = "basicAuth")
public class TodoController implements TodoService {
    private final TodoComponent todoComponent;
    @Override
    public TodoViewResponse createTodo(@Valid CreateTodoRequest request) {
        TodoViewResponse response = todoComponent.createTodo(request);
        return response;
    }

    @Override
    public TodoViewResponse getTodoById(Long id) {
        TodoViewResponse response = todoComponent.getTodoById(id);
        return response;
    }

    @Override
    public List<TodoViewResponse> getTodosByAccountId(Long accountId) {
        List<TodoViewResponse> responseList = todoComponent.getTodosByAccountId(accountId);
        return responseList;
    }

    @Override
    public void deleteTodo(Long id) {
        todoComponent.deleteTodo(id);
    }
}
