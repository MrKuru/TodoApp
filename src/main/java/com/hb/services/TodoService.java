package com.hb.services;


import com.hb.dto.requests.CreateTodoRequest;
import com.hb.dto.responses.TodoViewResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
public interface TodoService {
    @PostMapping("/todo")
    TodoViewResponse createTodo(@RequestBody CreateTodoRequest request);
    @GetMapping("/todo/{id}")
    TodoViewResponse getTodoById(@PathVariable Long id);
    @GetMapping("/todo/account/{accountId}")
    List<TodoViewResponse> getTodosByAccountId(@PathVariable Long accountId);
    @DeleteMapping("/todo/{id}")
    void deleteTodo(@PathVariable Long id);
}
