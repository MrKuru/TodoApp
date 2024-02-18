package com.hb.components;

import com.hb.dto.requests.CreateTaskRequest;
import com.hb.dto.responses.TaskViewResponse;
import com.hb.exception.exceptions.TaskNotFoundException;
import com.hb.exception.exceptions.TodoNotFoundException;
import com.hb.mappers.TaskMapper;
import com.hb.model.documents.Task;
import com.hb.model.documents.Todo;
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
class TaskComponentTest {

    @Mock
    private TaskRepository mockTaskRepository;

    @Mock
    private TaskMapper mockTaskMapper;

    @Mock
    private TodoRepository mockTodoRepository;

    @InjectMocks
    private TaskComponent mockTaskComponent;

    @Test
    void createTask_Success() {
        CreateTaskRequest request = new CreateTaskRequest(1L,"Title", "Description", 1L);
        when(mockTodoRepository.findById(1L)).thenReturn(Optional.of(new Todo()));
        when(mockTaskRepository.save(any(Task.class))).thenReturn(new Task());
        when(mockTaskMapper.toView(any(Task.class))).thenReturn(new TaskViewResponse());

        TaskViewResponse response = mockTaskComponent.createTask(request);

        verify(mockTodoRepository).findById(1L);
        verify(mockTaskRepository).save(any(Task.class));
        verify(mockTaskMapper).toView(any(Task.class));
        assertThat(response).isNotNull();
    }

    @Test
    void createTask_TodoNotFoundException() {
        CreateTaskRequest request = new CreateTaskRequest(2L,"Title", "Description", 2L);
        when(mockTodoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockTaskComponent.createTask(request))
                .isInstanceOf(TodoNotFoundException.class);
    }

    @Test
    void getTaskById_Success() {
        Task task = new Task();
        when(mockTaskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(mockTaskMapper.optionalToView(Optional.of(task))).thenReturn(new TaskViewResponse());

        TaskViewResponse response = mockTaskComponent.getTaskById(anyLong());

        verify(mockTaskRepository).findById(anyLong());
        verify(mockTaskMapper).optionalToView(Optional.of(task));
        assertThat(response).isNotNull();
    }

    @Test
    void getTaskById_TaskNotFoundException() {
        when(mockTaskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockTaskComponent.getTaskById(anyLong()))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void getTasksByTodoId_Success() {
        List<Task> taskList = List.of(new Task(), new Task());
        when(mockTaskRepository.findByTodoId(anyLong())).thenReturn(taskList);
        when(mockTaskMapper.toViewList(taskList)).thenReturn(List.of(new TaskViewResponse(), new TaskViewResponse()));

        List<TaskViewResponse> responseList = mockTaskComponent.getTasksByTodoId(anyLong());

        verify(mockTaskRepository).findByTodoId(anyLong());
        verify(mockTaskMapper).toViewList(taskList);
        assertThat(responseList).isNotNull().hasSize(2);
    }

    @Test
    void deleteTask_Success() {
        Long taskId = 1L;
        when(mockTaskRepository.existsById(taskId)).thenReturn(true);

        mockTaskComponent.deleteTask(taskId);

        verify(mockTaskRepository).existsById(taskId);
        verify(mockTaskRepository).deleteById(taskId);
    }

    @Test
    void deleteTask_TaskNotFoundException() {
        Long taskId = 2L;
        when(mockTaskRepository.existsById(taskId)).thenReturn(false);

        assertThatThrownBy(() -> mockTaskComponent.deleteTask(taskId))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
