package com.hb.model.repositories;


import com.hb.model.documents.Task;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CouchbaseRepository<Task, Long> {
    List<Task> findByTodoId(Long todoId);
}
