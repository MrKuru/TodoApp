package com.hb.components;


import com.hb.dto.requests.CreateTodoRequest;
import com.hb.dto.responses.TodoViewResponse;
import com.hb.exception.exceptions.AccountNotFoundException;
import com.hb.exception.exceptions.TodoNotFoundException;
import com.hb.exception.exceptions.TodoWithTaskException;
import com.hb.mappers.TodoMapper;
import com.hb.model.documents.Account;
import com.hb.model.documents.Task;
import com.hb.model.documents.Todo;
import com.hb.model.repositories.AccountRepository;
import com.hb.model.repositories.TaskRepository;
import com.hb.model.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TodoComponent {
    private final TaskRepository taskRepository;
    private final TodoRepository repository;
    private final AccountRepository accountRepository;
    private final TodoMapper mapper;
    public TodoViewResponse createTodo(CreateTodoRequest request) {
        Optional<Account> optionalAccount = accountRepository.findById(request.getAccountId());
        if (optionalAccount.isEmpty()){
            throw new AccountNotFoundException("Account not found with id: "+ request.getAccountId());
        }
        Todo todo = Todo.builder()
                .id(request.getId())
                .listName(request.getListName())
                .accountId(request.getAccountId())
                .build();
        repository.save(todo);
        return mapper.toView(todo);
    }

    public TodoViewResponse getTodoById(Long id) {
        Optional<Todo> optionalTodo = repository.findById(id);
        if (optionalTodo.isEmpty()){
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        return mapper.optionalToView(optionalTodo);
    }

    public List<TodoViewResponse> getTodosByAccountId(Long accountId) {
        List<Todo> todoList = repository.findByAccountId(accountId);
        return mapper.toViewList(todoList);
    }

    public void deleteTodo(Long id) {
        List<Task> taskList = taskRepository.findByTodoId(id);
        if (!taskList.isEmpty()) {
            throw new TodoWithTaskException("Todo with id " + id + " has associated tasks. Cannot delete.");
        }
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
    }
}
