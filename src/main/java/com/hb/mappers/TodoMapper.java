package com.hb.mappers;


import com.hb.dto.responses.TodoViewResponse;
import com.hb.model.documents.Todo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TodoMapper {
    public TodoViewResponse toView(Todo todo) {
        return TodoViewResponse.builder()
                .id(todo.getId())
                .listName(todo.getListName())
                .accountId(todo.getAccountId())
                .build();
    }

    public TodoViewResponse optionalToView(Optional<Todo> optionalTodo) {
        return TodoViewResponse.builder()
                .id(optionalTodo.get().getId())
                .listName(optionalTodo.get().getListName())
                .accountId(optionalTodo.get().getAccountId())
                .build();
    }

    public List<TodoViewResponse> toViewList(List<Todo> todoList) {
        return todoList.stream()
                .map(this::toView)
                .collect(Collectors.toList());
    }
}
