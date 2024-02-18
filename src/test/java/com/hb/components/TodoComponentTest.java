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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoComponentTest {

    @Mock
    private TaskRepository mockTaskRepository;

    @Mock
    private TodoRepository mockTodoRepository;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private TodoMapper mockTodoMapper;

    @InjectMocks
    private TodoComponent mockTodoComponent;

    @Test
    void createTodo_Success() {
        CreateTodoRequest request = new CreateTodoRequest(1L,"listName", 1L);
        when(mockAccountRepository.findById(1L)).thenReturn(Optional.of(new Account()));
        when(mockTodoRepository.save(any(Todo.class))).thenReturn(new Todo());
        when(mockTodoMapper.toView(any(Todo.class))).thenReturn(new TodoViewResponse());

        TodoViewResponse response = mockTodoComponent.createTodo(request);

        verify(mockAccountRepository).findById(1L);
        verify(mockTodoRepository).save(any(Todo.class));
        verify(mockTodoMapper).toView(any(Todo.class));
        assertThat(response).isNotNull();
    }

    @Test
    void createTodo_AccountNotFoundException() {
        CreateTodoRequest request = new CreateTodoRequest(1L,"listName", 2L);
        when(mockAccountRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockTodoComponent.createTodo(request))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void getTodoById_Success() {
        Todo todo = new Todo();
        when(mockTodoRepository.findById(anyLong())).thenReturn(Optional.of(todo));
        when(mockTodoMapper.optionalToView(Optional.of(todo))).thenReturn(new TodoViewResponse());

        TodoViewResponse response = mockTodoComponent.getTodoById(anyLong());

        verify(mockTodoRepository).findById(anyLong());
        verify(mockTodoMapper).optionalToView(Optional.of(todo));
        assertThat(response).isNotNull();
    }

    @Test
    void getTodoById_TodoNotFoundException() {
        when(mockTodoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockTodoComponent.getTodoById(anyLong()))
                .isInstanceOf(TodoNotFoundException.class);
    }

    @Test
    void getTodosByAccountId_Success() {
        List<Todo> todoList = List.of(new Todo(), new Todo());
        when(mockTodoRepository.findByAccountId(anyLong())).thenReturn(todoList);
        when(mockTodoMapper.toViewList(todoList)).thenReturn(List.of(new TodoViewResponse(), new TodoViewResponse()));

        List<TodoViewResponse> responseList = mockTodoComponent.getTodosByAccountId(anyLong());

        verify(mockTodoRepository).findByAccountId(anyLong());
        verify(mockTodoMapper).toViewList(todoList);
        assertThat(responseList).isNotEmpty();
    }

    @Test
    void deleteTodo_Success() {
        Long todoId = 1L;
        when(mockTaskRepository.findByTodoId(todoId)).thenReturn(List.of());
        when(mockTodoRepository.existsById(todoId)).thenReturn(true);

        mockTodoComponent.deleteTodo(todoId);

        verify(mockTaskRepository).findByTodoId(todoId);
        verify(mockTodoRepository).existsById(todoId);
        verify(mockTodoRepository).deleteById(todoId);
    }

    @Test
    void deleteTodo_TodoWithTaskException() {
        Long todoId = 2L;
        when(mockTaskRepository.findByTodoId(todoId)).thenReturn(List.of(new Task()));

        assertThatThrownBy(() -> mockTodoComponent.deleteTodo(todoId))
                .isInstanceOf(TodoWithTaskException.class);
    }

    @Test
    void deleteTodo_TodoNotFoundException() {
        Long todoId = 3L;
        when(mockTaskRepository.findByTodoId(todoId)).thenReturn(List.of());
        when(mockTodoRepository.existsById(todoId)).thenReturn(false);

        assertThatThrownBy(() -> mockTodoComponent.deleteTodo(todoId))
                .isInstanceOf(TodoNotFoundException.class);
    }
}
